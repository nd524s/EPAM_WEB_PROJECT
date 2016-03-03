package com.epam.agency.command;

import com.epam.agency.domain.Tour;
import com.epam.agency.util.ResourceManager;
import com.epam.agency.service.TourService;
import com.epam.agency.service.impl.TourServiceImpl;
import com.epam.agency.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 02.02.2016.
 */
public class ToursOfCountryCommand implements ActionCommand {
    private static final String PARAM_ID = "id";
    private static final String ATTR_WARNING = "warn";
    private static final String ATTR_TOURS = "tours";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        TourService service = TourServiceImpl.getInstance();
        String page = ResourceManager.getProperty("page.tours");
        long id = Long.parseLong(request.getParameter(PARAM_ID));
        ArrayList<Tour> tours = service.getToursByCountry(id);

        if(tours.isEmpty()) {
            request.setAttribute(ATTR_WARNING, ServerMessage.message(request.getSession(), "message.noTours"));
        }
        request.setAttribute(ATTR_TOURS, tours);
        return page;
    }
}
