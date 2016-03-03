package com.epam.agency.pool;

import com.epam.agency.util.ResourceManager;
import com.epam.agency.pool.exception.ConnectionPoolException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 28.01.2016.
 */
public class    ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static final int DEFAULT_POOL_SIZE = 10;
    private static AtomicBoolean poolAtomic = new AtomicBoolean(false);
    private String url;
    private String password;
    private String user;
    private int poolSize;

    private ConnectionPool() {
        password = ResourceManager.getProperty("db.password");
        user = ResourceManager.getProperty("db.user");
        url = ResourceManager.getProperty("db.url");

        try{
            poolSize = Integer.parseInt(ResourceManager.getProperty("db.poolSize"));
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
        }

        try {
            initPool();
        } catch (ConnectionPoolException e) {
            logger.fatal("Fatal error with connecting to database: " + e);
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance() {
        if(!poolAtomic.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    poolAtomic.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Init pool by "Proxy connection" objects and gives three attempts for each connection.
     * @throws ConnectionPoolException
     */
    private void initPool() throws ConnectionPoolException {
        connectionQueue = new ArrayBlockingQueue<>(poolSize);

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            int i = 0;
            while (i <= poolSize * 3) {
                Connection connection = DriverManager.getConnection(url, user, password);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connectionQueue.offer(proxyConnection);
                i++;
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Pool init error", e);
        }
    }

    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection proxyConnection;
        try {
            proxyConnection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Getting from pool error", e);
        }
        return proxyConnection;
    }

    /**
     * Returns "ProxyConnection" object in pool.
     * @param proxyConnection
     */
    void closeConnection(ProxyConnection proxyConnection) {
        connectionQueue.offer(proxyConnection);
    }

    /**
     * Close all "Proxy connection" objects in pool.
     */
    public void destroyConnections(){
        ProxyConnection proxyConnection;

        while ((proxyConnection = connectionQueue.poll()) != null) {
            try {
                proxyConnection.destroyConnection();
            } catch (SQLException e) {
                logger.error("Can not close connections");
            }
        }
    }
}
