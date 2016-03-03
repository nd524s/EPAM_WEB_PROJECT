package test.com.epam.agency;

import com.epam.agency.util.PasswordHash;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Никита on 01.03.2016.
 */
public class PasswordHashTest {
    private static final String ADMIN_HASH = "21232f297a57a5a743894a0e4a801fc3";
    private static final String ADMIN = "admin";

    @Test
    public void compareHashes(){
        Assert.assertEquals(ADMIN_HASH, PasswordHash.md5Hashing(ADMIN));
    }
}
