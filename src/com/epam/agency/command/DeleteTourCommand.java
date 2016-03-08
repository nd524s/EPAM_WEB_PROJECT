package com.epam.agency.command;

import com.epam.agency.service.TourService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.TourServiceImpl;
import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 28.02.2016.
 */
public class DeleteTourCommand implements ActionCommand {
    private static final String PARAM_TOUR_ID = "tourId";
    private static final String ATTR_URL = "url";
    private static final String HEADER_ATTR = "referer";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long tourId = Long.parseLong(request.getParameter(PARAM_TOUR_ID));
        TourService tourService = TourServiceImpl.getInstance();

        if(! tourService.delete(tourId)) {
            request.setAttribute(ATTR_URL, request.getHeader(HEADER_ATTR));
            return ResourceManager.getProperty("page.delete");
        }
        return request.getHeader(HEADER_ATTR);
    }
}
