package com.epam.agency.util;

import java.util.ResourceBundle;

/**
 * Created by Никита on 29.01.2016.
 */
public class ResourceManager {
    private static final ResourceBundle resourceBundle =
                         ResourceBundle.getBundle("resources.project");
    private static final ResourceBundle messageBundle =
                         ResourceBundle.getBundle("resources.caption");
    private static final ResourceBundle messageEnBundle =
            ResourceBundle.getBundle("resources.caption_en_US");

    private ResourceManager(){}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }

    public static String getRuMessage(String key) { return messageBundle.getString(key); }

    public static String getEnMessage(String key) { return messageEnBundle.getString(key); }
}
