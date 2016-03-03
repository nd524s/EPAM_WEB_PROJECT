package com.epam.agency.command;

import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 31.01.2016.
 */
public interface ActionCommand {
    String execute(HttpServletRequest request) throws ServiceException;
}
