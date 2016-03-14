package com.epam.agency.command.check;


import com.epam.agency.command.ServerMessage;
import com.epam.agency.domain.Tour;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.TourServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

/**
 * Created by Никита on 10.03.2016.
 */
public class TourItemsCheck {

    public static String checkItems(HttpSession session, long tourId, int totalNum, int num) throws ServiceException {
        String warn = null;
        int itemsNumber = 0;

        Tour tour = TourServiceImpl.getInstance().getTourById(tourId);
        if(tour != null) {
            itemsNumber = tour.getNumberOfSeats();
        } else {
            warn = ServerMessage.message(session, "message.tour");
        }

        if(num < 1 || num > totalNum || totalNum != itemsNumber) {
            warn = ServerMessage.message(session, "message.items");
        }
        return warn;
    }
}
