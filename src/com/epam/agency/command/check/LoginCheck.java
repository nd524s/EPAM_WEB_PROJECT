package com.epam.agency.command.check;

import com.epam.agency.command.ServerMessage;
import com.epam.agency.validator.Validator;

import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 22.02.2016.
 */
public class LoginCheck {
    public static String checkLoginData(HttpSession session, String username, String password) {
        String warn = null;

        if (!Validator.validateUsername(username)) {
            warn = ServerMessage.message(session, "message.username");
        } else if (!Validator.validatePassword(password)) {
            warn = ServerMessage.message(session, "message.password");
        }
        return warn;
    }
}
