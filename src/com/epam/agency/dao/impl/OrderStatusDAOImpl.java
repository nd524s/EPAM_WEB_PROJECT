package com.epam.agency.dao.impl;

import com.epam.agency.dao.OrderStatusDAO;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.OrderStatus;
import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 17.02.2016.
 */
public class OrderStatusDAOImpl implements OrderStatusDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();;
    private static final OrderStatusDAOImpl instance = new OrderStatusDAOImpl();
    private static final String SQL_GET_STATUS_BY_ID = "SELECT status_id, status_name FROM order_status" +
                                                       " WHERE status_id=?";
    private OrderStatusDAOImpl() {
    }

    public static OrderStatusDAOImpl getInstance() {
        return instance;
    }

    @Override
    public OrderStatus getById(long id) throws DAOException {
        OrderStatus orderStatus = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long statusId = resultSet.getLong("status_id");
                    String statusName = resultSet.getString("status_name");
                    orderStatus = new OrderStatus(statusId, statusName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get order status by id", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return orderStatus;
    }

    @Override
    public void create(OrderStatus item) throws DAOException {

    }

    @Override
    public void delete(OrderStatus item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ArrayList<OrderStatus> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(OrderStatus item) {

    }
}
