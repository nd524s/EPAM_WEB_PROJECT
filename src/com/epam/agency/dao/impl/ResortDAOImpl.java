package com.epam.agency.dao.impl;

import com.epam.agency.dao.CountryDAO;
import com.epam.agency.dao.GenericDAO;
import com.epam.agency.dao.ResortDAO;
import com.epam.agency.domain.Country;
import com.epam.agency.domain.Resort;
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
public class ResortDAOImpl implements ResortDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();;
    private static final ResortDAOImpl instance = new ResortDAOImpl();
    private static final String SQL_GET_RESORT_BY_ID = "SELECT resort_id, resort_name, country_id" +
                                                       " FROM resort WHERE resort_id=?";
    private static final String SQL_GET_ALL_RESORTS = "SELECT resort_id, resort_name, country_id" +
                                                      " FROM resort ";
    private ResortDAOImpl() {
    }

    public static ResortDAOImpl getInstance() {
        return instance;
    }

    @Override
    public Resort getById(long id) throws DAOException {
        CountryDAO countryDAO = CountryDAOImpl.getInstance();
        Resort resort = null;
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_RESORT_BY_ID)) {
            preparedStatement.setLong(1,id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long resortId = resultSet.getLong("resort_id");
                    String resortName = resultSet.getString("resort_name");
                    Country country = countryDAO.getById(resultSet.getLong("country_id"));
                    resort = new Resort(resortId, resortName, country);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get resort by ID", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return resort;
    }

    @Override
    public ArrayList<Resort> getAll() throws DAOException {
        CountryDAO countryDAO = CountryDAOImpl.getInstance();
        ArrayList<Resort> resorts = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_RESORTS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long resortId = resultSet.getLong("resort_id");
                String resortName = resultSet.getString("resort_name");
                Country country = countryDAO.getById(resultSet.getLong("country_id"));
                resorts.add(new Resort(resortId, resortName, country));
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get resort by ID", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return resorts;
    }

    @Override
    public void update(Resort item) {

    }
    @Override
    public void create(Resort item) throws DAOException {

    }

    @Override
    public void delete(Resort item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }

}
