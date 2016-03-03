package com.epam.agency.service;

import com.epam.agency.domain.OrderList;
import com.epam.agency.service.exception.ServiceException;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Никита on 16.02.2016.
 */
public interface OrderListService {
    void orderTour(long tourId, long userId, Date date, int itemNumber) throws ServiceException;
    ArrayList<OrderList> getUserOrders(long id) throws ServiceException;
    void deleteOrder(long id) throws ServiceException;
    ArrayList<OrderList> getAllOrders() throws ServiceException;
    void processOrder(long statusId, long orderId) throws ServiceException;
}