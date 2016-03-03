package com.epam.agency.dao;

import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.Tour;

import java.util.ArrayList;

/**
 * Created by Никита on 14.02.2016.
 */
public interface TourDAO extends GenericDAO<Tour> {
    ArrayList<Tour> getToursByCountry(long countryId) throws DAOException;
    ArrayList<Tour> getToursByResort(long id) throws DAOException;
    void update(long tourId, int numberOfSeats) throws DAOException;

}
