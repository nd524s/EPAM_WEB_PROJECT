package com.epam.agency.service;

import com.epam.agency.domain.Resort;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by Никита on 14.02.2016.
 */
public interface ResortService {
    ArrayList<Resort> getAllResorts() throws ServiceException;
}
