package com.epam.agency.command;

import com.epam.agency.domain.Resort;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.ResortService;
import com.epam.agency.service.impl.ResortServiceImpl;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 04.02.2016.
 */
public class ViewAllResortsCommand implements ActionCommand{
    private static final String ATTR_WARNING = "warn";
    private static final String ATTR_RESORTS = "resorts";
    
    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        ResortService resortServiceImpl = ResortServiceImpl.getInstance();
        String page = ResourceManager.getProperty("page.resorts");
        ArrayList<Resort> resorts = resortServiceImpl.getAllResorts();

        if (resorts.isEmpty()) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.noResorts"));
        }
        request.setAttribute(ATTR_RESORTS, resorts);
        return page;
    }
}
