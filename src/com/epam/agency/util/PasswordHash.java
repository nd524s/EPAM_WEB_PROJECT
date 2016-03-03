package com.epam.agency.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Никита on 08.02.2016.
 */
public class PasswordHash {
    private static Logger logger = LogManager.getLogger(PasswordHash.class);

    /**
     * Do MD5 hashing of user password.
     * @param st
     * @return hashing value of input string.
     */
    public static String md5Hashing(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error("No such parameter for MessageDigest.getInstance method");
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
