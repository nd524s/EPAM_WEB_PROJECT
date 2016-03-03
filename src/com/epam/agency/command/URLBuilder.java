package com.epam.agency.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 18.02.2016.
 */
public class URLBuilder {

    /**
     * Build URL with two parameters for redirect.
     * @param requestURL
     * @param command
     * @param parameterName
     * @param parameterValue
     * @return URL.
     */
    public static String buildFullURL(StringBuffer requestURL, String command,
                                      String parameterName, String parameterValue) {
        return requestURL.append('?').append("command=" + command)
                .append('&').append(parameterName + "=" + parameterValue).toString();
    }

    /**
     * Build URL with one parameter for redirect.
     * @param requestURL
     * @param command
     * @return URL.
     */
    public static String buildFullURL(StringBuffer requestURL, String command) {
        return requestURL.append('?').append("command=" + command).toString();
    }
}
