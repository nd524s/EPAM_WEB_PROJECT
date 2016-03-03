package com.epam.agency.command.check;

import com.epam.agency.command.ServerMessage;
import com.epam.agency.validator.Validator;

import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 22.02.2016.
 */
public class RegistrationCheck {
    public static String checkRegistrationData(HttpSession session, String firstName, String lastName,
                                               String telNumber, String username, String password) {
        String warn = null;

        if (!Validator.validateName(firstName)) {
            warn = ServerMessage.message(session, "message.firstName");
        } else if (!Validator.validateName(lastName)) {
            warn = ServerMessage.message(session, "message.lastName");
        } else if (!Validator.validateTelNumber(telNumber)) {
            warn = ServerMessage.message(session, "message.telNumber");
        } else if (!Validator.validateUsername(username)) {
            warn = ServerMessage.message(session, "message.username");
        } else if (!Validator.validatePassword(password)) {
            warn = ServerMessage.message(session, "message.password");
        }
        return warn;
    }
}
