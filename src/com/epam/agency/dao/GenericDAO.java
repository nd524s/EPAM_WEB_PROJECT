package com.epam.agency.dao;

import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.domain.Entity;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.util.ArrayList;

/**
 * Created by Никита on 30.01.2016.
 */
public interface GenericDAO<T extends Entity> {
    void create(T item) throws DAOException;
    void delete(T item) throws DAOException;
    void delete(long id) throws DAOException;
    T getById(long id) throws DAOException;
    ArrayList<T> getAll() throws DAOException;
    void update(T item) throws DAOException;
}
