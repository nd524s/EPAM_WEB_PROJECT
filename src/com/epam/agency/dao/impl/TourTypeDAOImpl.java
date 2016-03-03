package com.epam.agency.dao.impl;

import com.epam.agency.dao.TourTypeDAO;
import com.epam.agency.domain.TourType;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 03.02.2016.
 */
public class TourTypeDAOImpl implements TourTypeDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final TourTypeDAOImpl instance = new TourTypeDAOImpl();
    private static final String SQL_GET_TOUR_TYPE_BY_ID = "SELECT type_id, type_name" +
                                                    " FROM tour_type WHERE type_id=?";
    private static final String SQL_GET_TOUR_TYPES = "SELECT type_id, type_name" +
                                                    " FROM tour_type";

    private TourTypeDAOImpl() {
    }

    public static TourTypeDAOImpl getInstance() {
        return instance;
    }

    @Override
    public TourType getById(long id) throws DAOException {
        TourType tourType = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TOUR_TYPE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long typeId = resultSet.getLong("type_id");
                    String typeName = resultSet.getString("type_name");
                    tourType = new TourType(typeId, typeName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get tour type by ID", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tourType;
    }

    @Override
    public ArrayList<TourType> getAll() throws DAOException {
        ArrayList<TourType> tourTypes = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TOUR_TYPES)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long typeId = resultSet.getLong("type_id");
                    String typeName = resultSet.getString("type_name");
                    tourTypes.add(new TourType(typeId, typeName));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get all tour types ", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tourTypes;
    }

    @Override
    public void create(TourType item) throws DAOException {

    }

    @Override
    public void delete(TourType item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }


    @Override
    public void update(TourType item) {

    }
}
