package com.epam.agency.command;

import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.OrderListServiceImpl;
import com.epam.agency.service.impl.TourServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Никита on 16.02.2016.
 */
public class OrderTourCommand implements ActionCommand {
    private static final String PARAM_TOUR_ID = "tourId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_ITEM_NUMBER = "num";
    private static final String PARAM_ALL_ITEMS = "totalNum";
    private static final String PARAM_PREVIOUS_COMMAND = "previousCommand";
    private static final String PARAM_ID = "id";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long tourId = Long.parseLong(request.getParameter(PARAM_TOUR_ID));
        int allItems = Integer.parseInt(request.getParameter(PARAM_ALL_ITEMS));
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Date date = java.sql.Date.valueOf(modifiedDate);
        long userId = Long.parseLong(request.getParameter(PARAM_USER_ID));
        int itemNumber = Integer.parseInt(request.getParameter(PARAM_ITEM_NUMBER));

        OrderListServiceImpl.getInstance().orderTour(tourId, userId, (java.sql.Date) date, itemNumber);
        TourServiceImpl.getInstance().updateSeatsNumber(tourId, allItems, itemNumber);
        return URLBuilder.buildFullURL(request.getRequestURL(), request.getParameter(PARAM_PREVIOUS_COMMAND),
                                       PARAM_ID, request.getParameter(PARAM_ID));
    }

}


