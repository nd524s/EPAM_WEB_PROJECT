package com.epam.agency.command;

import com.epam.agency.domain.Tour;
import com.epam.agency.service.TourService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.TourServiceImpl;
import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Никита on 28.02.2016.
 */
public class GetBurningToursCommand implements ActionCommand {
    private static final String ATTR_TOURS = "tours";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page = ResourceManager.getProperty("page.main");
        TourService tourService = TourServiceImpl.getInstance();
        ArrayList<Tour> tours = tourService.getBurningTours();

        request.setAttribute(ATTR_TOURS, tours);
        return page;
    }
}
