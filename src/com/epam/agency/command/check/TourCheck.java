package com.epam.agency.command.check;

import com.epam.agency.command.ServerMessage;
import com.epam.agency.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 21.02.2016.
 */
public class TourCheck {
    public static String checkTourData(HttpSession session, String begDate, String endDate,
                                        String cost, String itemNumber) {
        String warn = null;

        if ( !Validator.validateDate(begDate, endDate)) {
            warn = ServerMessage.message(session, "message.invalidDate");
        } else if( !Validator.validateCost(cost)) {
            warn = ServerMessage.message(session, "message.invalidCost");
        } else if( !Validator.validateItemNumber(itemNumber)) {
            warn = ServerMessage.message(session, "message.invalidItem");
        }
        return warn;
    }
}
