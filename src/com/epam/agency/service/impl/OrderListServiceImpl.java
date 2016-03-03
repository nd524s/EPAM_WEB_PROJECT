package com.epam.agency.service.impl;

import com.epam.agency.dao.OrderListDAO;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.dao.impl.OrderListDAOImpl;
import com.epam.agency.domain.OrderList;
import com.epam.agency.service.OrderListService;
import com.epam.agency.service.exception.ServiceException;

import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 16.02.2016.
 */
public class OrderListServiceImpl implements OrderListService {
    private static final OrderListServiceImpl instance = new OrderListServiceImpl();

    private OrderListServiceImpl(){}

    public static OrderListServiceImpl getInstance() {
        return instance;
    }

    /**
     * Updates status of current order.
     * @param statusId
     * @param orderId
     * @throws ServiceException
     */
    @Override
    public void processOrder(long statusId, long orderId) throws ServiceException {
        OrderListDAO orderListDAO = OrderListDAOImpl.getInstance();

        try {
            orderListDAO.updateStatus(orderId, statusId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteOrder(long id) throws ServiceException {
        OrderListDAO orderListDAO = OrderListDAOImpl.getInstance();
        try {
            orderListDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ArrayList<OrderList> getAllOrders() throws ServiceException {
        OrderListDAO orderListDAO = OrderListDAOImpl.getInstance();
        ArrayList<OrderList> orders;

        try {
            orders = orderListDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public ArrayList<OrderList> getUserOrders(long id) throws ServiceException {
        OrderListDAO orderListDAO = OrderListDAOImpl.getInstance();
        ArrayList<OrderList> orders;

        try {
            orders = orderListDAO.getByUserId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return orders;
    }

    /**
     * Create new order for current user.
     * @param tourId
     * @param userId
     * @param date
     * @param itemNumber
     * @throws ServiceException
     */
    @Override
    public void orderTour(long tourId, long userId, Date date, int itemNumber) throws ServiceException {
        OrderListDAO orderListDAO = OrderListDAOImpl.getInstance();
        try {
            orderListDAO.create(tourId, userId, date, itemNumber);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
