package com.epam.agency.command;

import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 20.02.2016.
 */
public class ServerMessage {
    private static final String ATTR_LANGUAGE = "language";

    /**
     * Dependens on the current locale, returns
     * a message in the appropriate language.
     * @param session
     * @param messageKey
     * @return Message.
     */
    public static String message(HttpSession session, String messageKey) {
        String language = (String) session.getAttribute(ATTR_LANGUAGE);
        String message;

        if("ru_Ru".equals(language)) {
            message = ResourceManager.getRuMessage(messageKey);
        } else {
            message = ResourceManager.getEnMessage(messageKey);
        }
        return message;
    }
}
