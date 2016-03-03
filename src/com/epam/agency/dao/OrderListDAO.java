package com.epam.agency.dao;

import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.OrderList;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Никита on 13.02.2016.
 */
public interface OrderListDAO extends GenericDAO<OrderList> {
    void create(long tourId, long userId, Date date, int itemNumber) throws DAOException;
    ArrayList<OrderList> getByUserId(long userId) throws DAOException;
    void updateStatus(long orderId, long statusId) throws DAOException;
}
