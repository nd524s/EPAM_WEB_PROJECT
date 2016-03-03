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
 * Created by Никита on 21.02.2016.
 */
public class UpdateTourCommand implements ActionCommand {
    private static final String PARAM_TOUR_ID = "tourId";
    private static final String PARAM_TOUR_TYPE_ID = "typeId";
    private static final String PARAM_RESORT_ID = "resortId";
    private static final String PARAM_COST = "cost";
    private static final String PARAM_DISCRIPTION = "discription";
    private static final String PARAM_TOUR_OPERATOR_ID = "operatorId";
    private static final String PARAM_ITEM_NUMBER = "seats";
    private static final String PARAM_BEG_DATE = "begDate";
    private static final String PARAM_END_DATE = "endDate";
    private static final String PARAM_STATUS = "status";
    private static final String ATTR_TOUR_TYPES = "tourTypes";
    private static final String ATTR_RESORTS = "resorts";
    private static final String ATTR_TOUR_OPERATORS = "tourOperators";
    private static final String ATTR_MESSAGE = "message";
    private static final String ATTR_WARN = "warn";

    @Override
    public String execute(HttpServletRequest request) throws ServiceException {
        TourService tourService = TourServiceImpl.getInstance();
        long tourId = Long.parseLong(request.getParameter(PARAM_TOUR_ID));
        long tourTypeId = Long.parseLong(request.getParameter(PARAM_TOUR_TYPE_ID));
        long resortId = Long.parseLong(request.getParameter(PARAM_RESORT_ID));
        long tourOperatorId = Long.parseLong(request.getParameter(PARAM_TOUR_OPERATOR_ID));
        String discription = request.getParameter(PARAM_DISCRIPTION);
        int status = Integer.parseInt(request.getParameter(PARAM_STATUS));

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

        tourService.update(new Tour(tourId, begDate, endDate, new TourType(tourTypeId), new Resort(resortId),
                           cost, discription, new TourOperator(tourOperatorId), itemNumber, status));
        request.setAttribute(ATTR_MESSAGE, ServerMessage.message(request.getSession(), "message.update"));
        return getTourInformation(request);
    }

    /**
     * Set necessary attributes, that will be used in
     * returning JSP page for dropdowns.
     * @param request
     * @return "updateTour.jsp".
     * @throws ServiceException
     */
    private String getTourInformation(HttpServletRequest request) throws ServiceException {
        TourTypeService tourTypeService = TourTypeServiceImpl.getInstance();
        ResortService resortService = ResortServiceImpl.getInstance();
        TourOperatorService tourOperatorService = TourOperatorServiceImpl.getInstance();
        request.setAttribute(ATTR_TOUR_TYPES, tourTypeService.getTourTypes());
        request.setAttribute(ATTR_RESORTS, resortService.getAllResorts());
        request.setAttribute(ATTR_TOUR_OPERATORS, tourOperatorService.getAllTourOperators());
        return ResourceManager.getProperty("page.updateTour");
    }
}
