package com.epam.agency.service.impl;

import com.epam.agency.dao.TourOperatorDAO;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.dao.impl.TourOperatorDaoImpl;
import com.epam.agency.domain.TourOperator;
import com.epam.agency.service.TourOperatorService;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 19.02.2016.
 */
public class TourOperatorServiceImpl implements TourOperatorService {
    private static final TourOperatorServiceImpl instance = new TourOperatorServiceImpl();

    private TourOperatorServiceImpl(){}

    public static TourOperatorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<TourOperator> getAllTourOperators() throws ServiceException {
        TourOperatorDAO operatorDAO = TourOperatorDaoImpl.getInstance();
        ArrayList<TourOperator> tourOperators;
        try {
            tourOperators = operatorDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return tourOperators;
    }
}
