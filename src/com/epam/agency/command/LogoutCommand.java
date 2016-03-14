package com.epam.agency.command;

import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 14.02.2016.
 */
public class LogoutCommand implements ActionCommand {

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String mainPage = ResourceManager.getProperty("page.index");
        request.getSession().invalidate();
        return mainPage;
    }
}
