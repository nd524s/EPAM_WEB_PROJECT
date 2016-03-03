package com.epam.agency.service.impl;

import com.epam.agency.dao.UserDAO;
import com.epam.agency.dao.impl.UserDAOImpl;
import com.epam.agency.domain.User;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.service.UserService;
import com.epam.agency.service.exception.ServiceException;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 05.02.2016.
 */
public class UserServiceImpl implements UserService {
    private static final UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance() {
        return instance;
    }

    /**
     * Add user, if he doesn't exist in system.
     * @param user
     * @return boolean value.
     * @throws ServiceException
     */
    public boolean addUser(User user) throws ServiceException {
        UserDAO userDAOImpl = UserDAOImpl.getInstance();
        try {
            if (checkUsername(user.getLogin())) {
                userDAOImpl.create(user);
                return true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    /**
     * Finds current login.
     * @param userLogin
     * @return boolean value.
     * @throws DAOException
     */
    private boolean checkUsername(String userLogin) throws DAOException {
        UserDAO userDAOImpl = UserDAOImpl.getInstance();
        String login = userDAOImpl.getLogin(userLogin);

        if (login == null) {
            return true;
        } else {
            return false;
        }
    }

    public User getByLoginPassword(String login, String password) throws ServiceException {
        UserDAO userDAOImpl = UserDAOImpl.getInstance();
        User user;

        try {
            user = userDAOImpl.getLoginPassword(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }
}
