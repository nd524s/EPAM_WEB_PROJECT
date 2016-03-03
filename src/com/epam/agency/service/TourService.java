package com.epam.agency.service;

import com.epam.agency.domain.Entity;
import com.epam.agency.domain.Tour;
import com.epam.agency.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Никита on 14.02.2016.
 */
public interface TourService {
    ArrayList<Tour> getToursByCountry(long countryId) throws ServiceException;
    ArrayList<Tour> getToursByResort(long resortId) throws ServiceException;
    ArrayList<Tour> getBurningTours() throws ServiceException;
    boolean delete(long tourId) throws ServiceException;
    void updateSeatsNumber(long tourId, int allItems, int currentItems) throws ServiceException;
    void createTour(Tour tour) throws ServiceException;
    void update(Tour tour) throws ServiceException;
}
