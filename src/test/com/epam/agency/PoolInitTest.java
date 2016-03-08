package test.com.epam.agency;

import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.util.ResourceManager;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Никита on 01.03.2016.
 */
public class PoolInitTest {
    private static final String URL = "jdbc:mysql://localhost:3306/epam";
    private static final String USER ="root";
    private static final String PASSWORD = "root";
    private static final String POOL_SIZE = "10";

    private ArrayList<String> initProperties() {
        ArrayList<String> list = new ArrayList<>(4);
        list.add(ResourceManager.getProperty("db.url"));
        list.add(ResourceManager.getProperty("db.user"));
        list.add(ResourceManager.getProperty("db.password"));
        list.add(ResourceManager.getProperty("db.poolSize"));
        return list;
    }

    @Test
    public void comparePropertiesTest() {
        ArrayList<String> properties = new ArrayList<>(4);
        properties.add(URL);
        properties.add(USER);
        properties.add(PASSWORD);
        properties.add(POOL_SIZE);
        Assert.assertEquals(properties, initProperties());
    }

    @Test
    public void instanceInitTest() {
        Assert.assertNotNull(ConnectionPool.getInstance());
    }

}
