package com.epam.agency.command;

import com.epam.agency.domain.OrderList;
import com.epam.agency.domain.User;
import com.epam.agency.service.OrderListService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.OrderListServiceImpl;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Никита on 18.02.2016.
 */
public class PayCommand implements ActionCommand {
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_STATUS_ID = "status";
    private static final String PARAM_CARD_NUMBER = "cardNumber";
    private static final String ATTR_WARNING = "warn";
    private static final String ATTR_ORDERS = "orders";
    private static final String SESSION_ATTR = "user";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String payPage = ResourceManager.getProperty("page.pay");
        String userPage = ResourceManager.getProperty("page.user");
        OrderListService orderListService = OrderListServiceImpl.getInstance();

        if( ! Validator.validateCardNumber(request.getParameter(PARAM_CARD_NUMBER))) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.invalidCardNumber"));
            return payPage;
        }
        long orderId = Long.parseLong(request.getParameter(PARAM_ORDER_ID));
        long statusId = Long.parseLong(request.getParameter(PARAM_STATUS_ID));
        orderListService.processOrder(statusId, orderId);
        ArrayList<OrderList> orders = getUserOrders(request);

        if(orders.isEmpty()) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.noOrders"));
        }
        request.setAttribute(ATTR_ORDERS, orders);

        return userPage;
    }

    /**
     * Gets list of orders for current user.
     * @param request
     * @return list of orders
     * @throws ServiceException
     */
    private ArrayList<OrderList> getUserOrders(HttpServletRequest request) throws ServiceException {
        OrderListService orderListService = OrderListServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SESSION_ATTR);
        long userId = user.getUserId();

        return orderListService.getUserOrders(userId);
    }
}
