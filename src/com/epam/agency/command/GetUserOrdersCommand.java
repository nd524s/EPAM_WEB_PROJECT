package com.epam.agency.command;

import com.epam.agency.domain.OrderList;
import com.epam.agency.domain.User;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.OrderListService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.OrderListServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Никита on 17.02.2016.
 */
public class GetUserOrdersCommand implements ActionCommand {
    private static final String ATTR_WARN = "warn";
    private static final String ATTR_ORDERS = "orders";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        OrderListService orderListService = OrderListServiceImpl.getInstance();
        String page = ResourceManager.getProperty("page.user");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        long userId = user.getUserId();
        ArrayList<OrderList> orders = orderListService.getUserOrders(userId);

        if(orders.isEmpty()) {
            request.setAttribute(ATTR_WARN, ServerMessage.message(request.getSession(), "message.noOrders"));
        }
        request.setAttribute(ATTR_ORDERS, orders);
        return page;
    }


}
