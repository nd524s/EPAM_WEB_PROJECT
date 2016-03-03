package com.epam.agency.service;

import com.epam.agency.domain.TourOperator;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by Никита on 19.02.2016.
 */
public interface TourOperatorService {
    ArrayList<TourOperator> getAllTourOperators() throws ServiceException;
}
