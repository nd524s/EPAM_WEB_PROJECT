package com.epam.agency.command;

import com.epam.agency.service.ResortService;
import com.epam.agency.service.TourOperatorService;
import com.epam.agency.service.TourTypeService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.ResortServiceImpl;
import com.epam.agency.service.impl.TourOperatorServiceImpl;
import com.epam.agency.service.impl.TourTypeServiceImpl;
import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Никита on 19.02.2016.
 */
public class GetAddDataCommand implements ActionCommand {
    private static final String ATTR_TOUR_TYPES = "tourTypes";
    private static final String ATTR_RESORTS = "resorts";
    private static final String ATTR_TOUR_OPERATORS = "tourOperators";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        String page = ResourceManager.getProperty("page.addTour");
        TourTypeService tourTypeService = TourTypeServiceImpl.getInstance();
        ResortService resortService = ResortServiceImpl.getInstance();
        TourOperatorService tourOperatorService = TourOperatorServiceImpl.getInstance();
        request.setAttribute(ATTR_TOUR_TYPES, tourTypeService.getTourTypes());
        request.setAttribute(ATTR_RESORTS, resortService.getAllResorts());
        request.setAttribute(ATTR_TOUR_OPERATORS, tourOperatorService.getAllTourOperators());
        return page;
    }
}
