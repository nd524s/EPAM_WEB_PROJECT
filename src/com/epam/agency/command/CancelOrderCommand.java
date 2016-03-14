package com.epam.agency.command;

import com.epam.agency.service.OrderListService;
import com.epam.agency.service.TourService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.OrderListServiceImpl;
import com.epam.agency.service.impl.TourServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 17.02.2016.
 */
public class CancelOrderCommand implements ActionCommand {
    private static final String PARAM_TOUR_ID = "tourId";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_TOUR_ITEMS = "totalNum";
    private static final String PARAM_ORDER_ITEMS = "itemNum";
    private static final String HEADER_ATTR = "referer";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long tourId = Long.parseLong(request.getParameter(PARAM_TOUR_ID));
        long orderId = Long.parseLong(request.getParameter(PARAM_ORDER_ID));
        int allItems = Integer.parseInt(request.getParameter(PARAM_TOUR_ITEMS));
        int orderItems = Integer.parseInt(request.getParameter(PARAM_ORDER_ITEMS));
        OrderListService orderListService = OrderListServiceImpl.getInstance();
        TourService tourService = TourServiceImpl.getInstance();

        orderListService.deleteOrder(orderId);
        tourService.updateSeatsNumber(tourId, allItems, -orderItems);
        return request.getHeader(HEADER_ATTR);
    }
}
