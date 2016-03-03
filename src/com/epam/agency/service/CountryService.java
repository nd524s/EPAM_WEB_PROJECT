package com.epam.agency.service;

import com.epam.agency.domain.Country;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by Никита on 14.02.2016.
 */
public interface CountryService {
    ArrayList<Country> getAllCountries() throws ServiceException;

}
