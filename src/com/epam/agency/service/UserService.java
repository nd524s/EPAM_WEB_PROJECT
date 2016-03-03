package com.epam.agency.service;

import com.epam.agency.domain.User;
import com.epam.agency.service.exception.ServiceException;

/**
 * Created by Никита on 14.02.2016.
 */
public interface UserService {
    boolean addUser(User user) throws ServiceException;
    User getByLoginPassword(String login, String password) throws ServiceException;
}
