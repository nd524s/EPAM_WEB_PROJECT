package com.epam.agency.service.impl;

import com.epam.agency.dao.ResortDAO;
import com.epam.agency.dao.impl.ResortDAOImpl;
import com.epam.agency.domain.Resort;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.service.ResortService;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 04.02.2016.
 */
public class ResortServiceImpl implements ResortService {
    private static ResortServiceImpl instance = new ResortServiceImpl();

    private ResortServiceImpl(){}

    public static ResortServiceImpl getInstance() {
        return instance;
    }

    public ArrayList<Resort> getAllResorts() throws ServiceException {
        ArrayList<Resort> resorts;
        ResortDAO resortDAO = ResortDAOImpl.getInstance();

        try {
            resorts = resortDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return resorts;
    }

}
