package com.epam.agency.listener;

import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.pool.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Никита on 02.02.2016.
 */

@WebListener
public class ContextListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logInitialized(servletContextEvent);
        connectionPoolInitialized();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroyConnections();
        logger.info("Connections with database closed");
    }

    private void connectionPoolInitialized() {
        ConnectionPool.getInstance();
        logger.info("Connection pool initialized");
    }

    private void logInitialized(ServletContextEvent servletContextEvent) {
        String prefix = servletContextEvent.getServletContext().getRealPath("/");
        String fileName = servletContextEvent.getServletContext().getInitParameter("log4j");
        if(fileName != null) {
            PropertyConfigurator.configure(prefix + fileName);
        }
        logger.info("Log4j initialized");
    }
}
