package com.epam.agency.dao.impl;

import com.epam.agency.dao.GenericDAO;
import com.epam.agency.dao.RoleDAO;
import com.epam.agency.dao.UserDAO;
import com.epam.agency.domain.Role;
import com.epam.agency.domain.User;
import com.epam.agency.dao.exception.DAOException;
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
 * Created by Никита on 05.02.2016.
 */
public class UserDAOImpl implements UserDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final UserDAOImpl instance = new UserDAOImpl();
    private static final String SQL_CHECK_USERNAME = "SELECT login FROM user WHERE login=?";
    private static final String SQL_CREATE_USER =
                                        "INSERT INTO user(f_name, l_name, login, password, tel_number)" +
                                        " VALUES(?,?,?,?,?)";
    private static final String SQL_CHECK_LOGIN_INFORMATION =
                                        "SELECT user_id, f_name, l_name, login, password, role_id, tel_number" +
                                        " FROM user WHERE login=? AND password=?";
    private static final String SQL_GET_USER_BY_ID = "SELECT user_id, f_name, l_name, login, password, role_id, tel_number" +
                                                     " FROM user WHERE user_id=?";

    private UserDAOImpl() {
    }

    public static UserDAOImpl getInstance() {
        return instance;
    }

    @Override
    public User getById(long id) throws DAOException {
        RoleDAO roleDAO = RoleDAOImpl.getInstance();
        User user = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    String firstName = resultSet.getString("f_name");
                    String lastName = resultSet.getString("l_name");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    Role role = roleDAO.getById(resultSet.getLong("role_id"));
                    String telNumber = resultSet.getString("tel_number");
                    user = new User(userId, firstName, lastName, password, login, role, telNumber);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get user by Id", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return user;
    }

    /**
     * Finds current username.
     * @param userLogin
     * @return "username" string or null.
     * @throws DAOException
     */
    @Override
    public String getLogin(String userLogin) throws DAOException {
        String login = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_USERNAME)) {
            preparedStatement.setString(1, userLogin);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    login = resultSet.getString("login");
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not user by login", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return login;
    }

    /**
     * Finds user with current login and password.
     * @param userName
     * @param userPassword
     * @return "User" object or null.
     * @throws DAOException
     */
    @Override
    public User getLoginPassword(String userName, String userPassword) throws DAOException {
        RoleDAO roleDAO = RoleDAOImpl.getInstance();
        User user = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN_INFORMATION)) {
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, userPassword);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long userId = resultSet.getLong("user_id");
                    String firstName = resultSet.getString("f_name");
                    String lastName = resultSet.getString("l_name");
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    Role role = roleDAO.getById(resultSet.getLong("role_id"));
                    String telNumber = resultSet.getString("tel_number");
                    user = new User(userId, firstName, lastName, password, login, role, telNumber);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get user information by login and password", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public void create(User user) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getLogin());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setString(5,user.getTelNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Can not create user", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(User item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public ArrayList<User> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(User item) {

    }
}
