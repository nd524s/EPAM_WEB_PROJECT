package com.epam.agency.dao.impl;

import com.epam.agency.dao.CountryDAO;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.domain.Country;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 31.01.2016.
 */
public class CountryDAOImpl implements CountryDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();;
    private static final CountryDAOImpl instance = new CountryDAOImpl();
    private static final String SQL_GET_ALL = "SELECT country_id, country_name FROM country";
    private static final String SQL_GET_COUNTRY = "SELECT country_id, country_name " +
                                                  "FROM country WHERE country_id=?";

    private CountryDAOImpl() {
    }

    public static CountryDAOImpl getInstance() {
        return instance;
    }

    @Override
    public Country getById(long id) throws DAOException {
        Country country = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COUNTRY)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long countryId = resultSet.getLong("country_id");
                    String countryName = resultSet.getString("country_name");
                    country = new Country(countryId, countryName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get current country", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return country;
    }

    @Override
    public ArrayList<Country> getAll() throws DAOException {
        ArrayList<Country> countries = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                long countryId = resultSet.getLong("country_id");
                String countryName = resultSet.getString("country_name");
                countries.add(new Country(countryId, countryName));
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get all countries", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return countries;
    }

    @Override
    public void update(Country item) {

    }

    @Override
    public void create(Country item) throws DAOException {

    }

    @Override
    public void delete(Country item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }

}
