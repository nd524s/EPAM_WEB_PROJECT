package com.epam.agency.dao;

import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.User;

/**
 * Created by Никита on 14.02.2016.
 */
public interface UserDAO extends GenericDAO<User> {
    String getLogin(String userLogin) throws DAOException;
    User getLoginPassword(String userName, String userPassword) throws DAOException;
}
