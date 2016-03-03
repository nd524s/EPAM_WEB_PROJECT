package com.epam.agency.command;

import com.epam.agency.domain.OrderList;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.OrderListService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.OrderListServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 18.02.2016.
 */
public class ViewAllOrdersCommand implements ActionCommand {
    private static final String ATTR_WARNING = "warn";
    private static final String ATTR_ORDERS = "orders";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page = ResourceManager.getProperty("page.allOrders");
        OrderListService orderListService = OrderListServiceImpl.getInstance();
        ArrayList<OrderList> orders = orderListService.getAllOrders();

        if(orders.isEmpty()) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.noOrders"));
        }
        request.setAttribute(ATTR_ORDERS, orders);
        return page;
    }
}
