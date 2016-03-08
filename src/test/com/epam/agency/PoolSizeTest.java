package test.com.epam.agency;


import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.util.ResourceManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Никита on 06.03.2016.
 */
public class PoolSizeTest {
    private static Logger logger = LogManager.getLogger(PoolSizeTest.class);
    private static final int POOL_SIZE =  Integer.parseInt(ResourceManager.getProperty("db.poolSize"));

    @Test
    public void poolSizeTest() {
        ConnectionPool pool = ConnectionPool.getInstance();
        try {
            Field queue = ConnectionPool.class.getDeclaredField("connectionQueue");
            queue.setAccessible(true);
            ArrayBlockingQueue<ProxyConnection> queueValue = (ArrayBlockingQueue<ProxyConnection>)queue.get(pool);
            Assert.assertEquals(queueValue.size(), POOL_SIZE);
        } catch (NoSuchFieldException e) {
            logger.error("No such field in ConnectionPool class", e);
        } catch (IllegalAccessException e) {
            logger.error("No access for current field", e);
        }
    }

}
