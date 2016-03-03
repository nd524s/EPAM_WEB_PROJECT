package com.epam.agency.service.impl;

import com.epam.agency.dao.TourTypeDAO;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.dao.impl.TourTypeDAOImpl;
import com.epam.agency.domain.TourType;
import com.epam.agency.service.TourTypeService;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 19.02.2016.
 */
public class TourTypeServiceImpl implements TourTypeService {
    private static final TourTypeServiceImpl instance = new TourTypeServiceImpl();

    private TourTypeServiceImpl(){}

    public static TourTypeServiceImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<TourType> getTourTypes() throws ServiceException {
        TourTypeDAO tourTypeDAO = TourTypeDAOImpl.getInstance();
        ArrayList<TourType> tourTypes;
        try {
            tourTypes = tourTypeDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourTypes;
    }
}
