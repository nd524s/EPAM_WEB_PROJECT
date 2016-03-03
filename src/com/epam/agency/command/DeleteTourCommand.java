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
    private static final String PARAM_PREVIOUS_COMMAND = "previousCommand";
    private static final String PARAM_ID = "id";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        long tourId = Long.parseLong(request.getParameter(PARAM_TOUR_ID));
        TourService tourService = TourServiceImpl.getInstance();

        if(! tourService.delete(tourId)) {
            request.setAttribute(ATTR_URL, chooseURL(request));
            return ResourceManager.getProperty("page.delete");
        }
        return chooseURL(request);
    }

    /**
     * Depending on the page where we call "DeleteCommand",
     * builds appropriate URL for redirect.
     * @param request
     * @return URL.
     */
    private String chooseURL(HttpServletRequest request) {
        String par = request.getParameter(PARAM_ID);
        if (par != null) {
            return URLBuilder.buildFullURL(request.getRequestURL(), request.getParameter(PARAM_PREVIOUS_COMMAND),
                                           PARAM_ID, request.getParameter(PARAM_ID));
        } else {
            return URLBuilder.buildFullURL(request.getRequestURL(), request.getParameter(PARAM_PREVIOUS_COMMAND));
        }
    }


}
