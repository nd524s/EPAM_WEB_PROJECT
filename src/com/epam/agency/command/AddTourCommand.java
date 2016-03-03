package com.epam.agency.command;

import com.epam.agency.command.check.TourCheck;
import com.epam.agency.domain.Resort;
import com.epam.agency.domain.Tour;
import com.epam.agency.domain.TourOperator;
import com.epam.agency.domain.TourType;
import com.epam.agency.service.ResortService;
import com.epam.agency.service.TourOperatorService;
import com.epam.agency.service.TourService;
import com.epam.agency.service.TourTypeService;
import com.epam.agency.service.exception.ServiceException;
import com.epam.agency.service.impl.ResortServiceImpl;
import com.epam.agency.service.impl.TourOperatorServiceImpl;
import com.epam.agency.service.impl.TourServiceImpl;
import com.epam.agency.service.impl.TourTypeServiceImpl;
import com.epam.agency.util.ResourceManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Created by Никита on 19.02.2016.
 */
public class AddTourCommand implements ActionCommand {
    private static final String PARAM_TOUR_TYPE_ID = "tourTypeId";
    private static final String PARAM_RESORT_ID = "resortId";
    private static final String PARAM_COST = "cost";
    private static final String PARAM_DISCRIPTION = "discription";
    private static final String PARAM_TOUR_OPERATOR_ID = "tourOperatorId";
    private static final String PARAM_ITEM_NUMBER = "itemNumber";
    private static final String PARAM_BEG_DATE = "begDate";
    private static final String PARAM_END_DATE = "endDate";
    private static final String PARAM_STATUS = "status";
    private static final String ATTR_MESSAGE_VALUE = "success";
    private static final String ATTR_TOUR_TYPES = "tourTypes";
    private static final String ATTR_RESORTS = "resorts";
    private static final String ATTR_TOUR_OPERATORS = "tourOperators";
    private static final String ATTR_MESSAGE = "message";
    private static final String COMMAND = "getAddData";
    private static final String ATTR_WARN = "warn";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        TourService tourService = TourServiceImpl.getInstance();
        long tourTypeId = Long.parseLong(request.getParameter(PARAM_TOUR_TYPE_ID));
        long resortId = Long.parseLong(request.getParameter(PARAM_RESORT_ID));
        long tourOperatorId = Long.parseLong(request.getParameter(PARAM_TOUR_OPERATOR_ID));
        int status = Integer.parseInt(request.getParameter(PARAM_STATUS));
        String discription = request.getParameter(PARAM_DISCRIPTION);

        String costStr = request.getParameter(PARAM_COST);
        String itemNumberStr = request.getParameter(PARAM_ITEM_NUMBER);
        String begDateStr = request.getParameter(PARAM_BEG_DATE);
        String endDateStr = request.getParameter(PARAM_END_DATE);
        String warn = TourCheck.checkTourData(request.getSession(), begDateStr, endDateStr, costStr, itemNumberStr);
        if(warn != null) {
            request.setAttribute(ATTR_WARN, warn);
            return getTourInformation(request);
        }
        double cost = Double.parseDouble(costStr);
        int itemNumber = Integer.parseInt(itemNumberStr);
        Date begDate = Date.valueOf(begDateStr);
        Date endDate = Date.valueOf(endDateStr);

        tourService.createTour(new Tour(begDate, endDate, new TourType(tourTypeId), new Resort(resortId),
                                        cost, discription, new TourOperator(tourOperatorId), itemNumber, status));
        return URLBuilder.buildFullURL(request.getRequestURL(), COMMAND, ATTR_MESSAGE, ATTR_MESSAGE_VALUE);
    }

    /**
     * Set necessary attributes, that will be used in
     * returning JSP page for dropdowns.
     * @param request
     * @return "addTour.jsp".
     * @throws ServiceException
     */
    private String getTourInformation(HttpServletRequest request) throws ServiceException {
        TourTypeService tourTypeService = TourTypeServiceImpl.getInstance();
        ResortService resortService = ResortServiceImpl.getInstance();
        TourOperatorService tourOperatorService = TourOperatorServiceImpl.getInstance();
        request.setAttribute(ATTR_TOUR_TYPES, tourTypeService.getTourTypes());
        request.setAttribute(ATTR_RESORTS, resortService.getAllResorts());
        request.setAttribute(ATTR_TOUR_OPERATORS, tourOperatorService.getAllTourOperators());
        return ResourceManager.getProperty("page.addTour");
    }
}
