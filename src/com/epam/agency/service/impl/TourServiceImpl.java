package com.epam.agency.service.impl;

import com.epam.agency.dao.CountryDAO;
import com.epam.agency.dao.TourDAO;
import com.epam.agency.dao.TourTypeDAO;
import com.epam.agency.dao.impl.CountryDAOImpl;
import com.epam.agency.dao.impl.TourDAOImpl;
import com.epam.agency.dao.impl.TourTypeDAOImpl;
import com.epam.agency.domain.Entity;
import com.epam.agency.domain.OrderList;
import com.epam.agency.domain.Tour;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.TourType;
import com.epam.agency.service.OrderListService;
import com.epam.agency.service.TourService;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 04.02.2016.
 */
public class TourServiceImpl implements TourService {
    private final static TourServiceImpl instance = new TourServiceImpl();
    private static final long PROCESSED_ID = 1;
    private static final long APPROVED_ID = 2;
    private final static int BURNING_TOUR_STATUS = 1;

    private TourServiceImpl(){}

    public static TourServiceImpl getInstance() {
        return instance;
    }

    /**
     * Finds orders with current "tourId", "process" and
     * "approve" states.
     * @param tourId
     * @return boolean value.
     * @throws ServiceException
     */
    @Override
    public boolean delete(long tourId) throws ServiceException {
        OrderListService orderListService = OrderListServiceImpl.getInstance();
        TourDAO tourDAO = TourDAOImpl.getInstance();
        ArrayList<OrderList> orders = orderListService.getAllOrders();
        int counter = 0;

        for(OrderList order : orders) {
            if(order.getTour().getTourId() == tourId && (order.getOrderStatus().getStatusId() == PROCESSED_ID
                                                     || order.getOrderStatus().getStatusId() == APPROVED_ID )) {
                counter++;
            }
        }
        if(counter == 0) {
            try {
                tourDAO.delete(tourId);
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<Tour> getBurningTours() throws ServiceException {
        TourDAO tourDAO = TourDAOImpl.getInstance();
        ArrayList<Tour> tours = new ArrayList<>();

        try {
            for(Tour tour : tourDAO.getAll()) {
                if (tour.getTourStatus() == BURNING_TOUR_STATUS) {
                    tours.add(tour);
                }
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tours;
    }

    @Override
    public void update(Tour tour) throws ServiceException {
        TourDAO tourDAO = TourDAOImpl.getInstance();
        try {
            tourDAO.update(tour);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void createTour(Tour tour) throws ServiceException {
        TourDAO tourDAO = TourDAOImpl.getInstance();
        try {
            tourDAO.create(tour);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateSeatsNumber(long tourId, int allItems, int currentItems) throws ServiceException {
        TourDAO tourDAO = TourDAOImpl.getInstance();
        int difference =  allItems - currentItems;
        try {
            tourDAO.update(tourId, difference);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public ArrayList<Tour> getToursByCountry(long countryId) throws ServiceException {
        ArrayList<Tour> tours;
        TourDAO tourDAOImpl = TourDAOImpl.getInstance();

        try {
            tours = tourDAOImpl.getToursByCountry(countryId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tours;
    }

    public ArrayList<Tour> getToursByResort(long resortId) throws ServiceException {
        ArrayList<Tour> tours;
        TourDAO tourDAOImpl = TourDAOImpl.getInstance();

        try {
            tours = tourDAOImpl.getToursByResort(resortId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tours;
    }



}
