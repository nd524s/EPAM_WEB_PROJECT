package com.epam.agency.dao.impl;

import com.epam.agency.dao.*;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.OrderStatus;
import com.epam.agency.domain.Tour;
import com.epam.agency.domain.User;
import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.domain.OrderList;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 30.01.2016.
 */
public class OrderListDAOImpl implements OrderListDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();;
    private static final OrderListDAOImpl instance = new OrderListDAOImpl();;
    private static final String SQL_CREATE_ORDER = "INSERT INTO order_list(user_id, tour_id, order_date, item_number)" +
                                                   " VALUES(?, ?, ?, ?)";
    private static final String SQL_DELETE_ORDER = "DELETE FROM order_list WHERE order_id = ?";
    private static final String SQL_GET_BY_USER_ID = "SELECT order_id, user_id, tour_id, order_date," +
                                                     " status_id, item_number FROM order_list WHERE user_id=?";
    private static final String SQL_GET_ALL_ORDERS = "SELECT order_id, user_id, tour_id, order_date," +
                                                     " status_id, item_number FROM order_list";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE order_list SET status_id=?" +
                                                          " WHERE order_id=?";
    private OrderListDAOImpl() {
    }

    public static OrderListDAOImpl getInstance() {
        return instance;
    }

    @Override
    public void updateStatus(long orderId, long statusId) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not updateNumberOfSeats status of current order ", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not delete order ", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public ArrayList<OrderList> getAll() throws DAOException {
        UserDAO userDAO = UserDAOImpl.getInstance();
        TourDAO tourDAO = TourDAOImpl.getInstance();
        OrderStatusDAO orderStatusDAO = OrderStatusDAOImpl.getInstance();
        ArrayList<OrderList> orders = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_ORDERS)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long orderId = resultSet.getLong("order_id");
                    User user = userDAO.getById(resultSet.getLong("user_id"));
                    Tour tour = tourDAO.getById(resultSet.getLong("tour_id"));
                    Date date = resultSet.getDate("order_date");
                    OrderStatus orderStatus = orderStatusDAO.getById(resultSet.getLong("status_id"));
                    int itemNumber = resultSet.getInt("item_number");
                    orders.add(new OrderList(orderId, user, tour, date, orderStatus, itemNumber));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get all orders", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return orders;

    }

    @Override
    public ArrayList<OrderList> getByUserId(long userId) throws DAOException {
        UserDAO userDAO = UserDAOImpl.getInstance();
        TourDAO tourDAO = TourDAOImpl.getInstance();
        OrderStatusDAO orderStatusDAO = OrderStatusDAOImpl.getInstance();
        ArrayList<OrderList> orders = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long orderId = resultSet.getLong("order_id");
                    User user = userDAO.getById(resultSet.getLong("user_id"));
                    Tour tour = tourDAO.getById(resultSet.getLong("tour_id"));
                    Date date = resultSet.getDate("order_date");
                    OrderStatus orderStatus = orderStatusDAO.getById(resultSet.getLong("status_id"));
                    int itemNumber = resultSet.getInt("item_number");
                    orders.add(new OrderList(orderId, user, tour, date, orderStatus, itemNumber));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get user oders by Id", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return orders;
    }

    @Override
    public void create(long tourId, long userId, Date date, int itemNumber) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_ORDER)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, tourId);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, itemNumber);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create order" + e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(OrderList item) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ORDER)) {
            preparedStatement.setLong(1, item.getOrderId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not delete order" + e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void create(OrderList item){

    }

    @Override
    public OrderList getById(long id) {
        return null;
    }

    @Override
    public void update(OrderList entity) {

    }
}
