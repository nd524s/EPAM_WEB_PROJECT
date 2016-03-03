package com.epam.agency.service.impl;

import com.epam.agency.dao.CountryDAO;
import com.epam.agency.dao.impl.CountryDAOImpl;
import com.epam.agency.domain.Country;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.service.CountryService;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 02.02.2016.
 */
public class CountryServiceImpl implements CountryService {
    private static final CountryServiceImpl instance = new CountryServiceImpl();

    private CountryServiceImpl(){}

    public static CountryServiceImpl getInstance() {
        return instance;
    }

    public ArrayList<Country> getAllCountries() throws ServiceException {
        ArrayList<Country> countries;
        CountryDAO countryDAOImpl = CountryDAOImpl.getInstance();

        try {
            countries = countryDAOImpl.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return countries;
    }


}
