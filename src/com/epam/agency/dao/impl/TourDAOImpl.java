package com.epam.agency.dao.impl;

import com.epam.agency.dao.*;
import com.epam.agency.domain.Resort;
import com.epam.agency.domain.TourOperator;
import com.epam.agency.domain.TourType;
import com.epam.agency.dao.exception.DAOException;
import com.epam.agency.pool.ConnectionPool;
import com.epam.agency.domain.Tour;
import com.epam.agency.pool.ProxyConnection;
import com.epam.agency.pool.exception.ConnectionPoolException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Никита on 30.01.2016.
 */
public class TourDAOImpl implements TourDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final TourDAOImpl instance = new TourDAOImpl();
    private static final String SQL_GET_TOURS_BY_COUNTRY =
                            "SELECT t.tour_id, t.beg_date, t.end_date, t.type_id, t.resort_id," +
                            "t.cost,t.discription, t.operator_id, t.number_of_seats, t.tour_status FROM tour t" +
                            " INNER JOIN resort r ON t.resort_id=r.resort_id" +
                            " WHERE r.country_id=? AND t.number_of_seats!=0";
    private static final String SQL_GET_TOURS_BY_RESORT =
                            "SELECT tour_id, beg_date, end_date, type_id, resort_id, " +
                            "cost, discription, operator_id, number_of_seats, tour_status" +
                            " FROM tour WHERE resort_id=? AND number_of_seats!=0 ";
    private static final String SQL_UPDATE_NUMBER_OF_SEATS = "UPDATE tour SET number_of_seats=?" +
                                                             " WHERE tour_id=?";
    private static final String SQL_GET_TOUR_BY_ID =
                            "SELECT tour_id, beg_date, end_date, type_id, resort_id, " +
                            "cost, discription, operator_id, number_of_seats, tour_status" +
                            " FROM tour WHERE tour_id=?";
    private static final String SQL_CREATE_TOUR =
                            "INSERT INTO tour(beg_date, end_date, type_id, resort_id, cost," +
                            " discription, operator_id, number_of_seats)" +
                            " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TOUR =
                            "UPDATE tour SET beg_date=?, end_date=?, type_id=?, resort_id=?, " +
                            "cost=?, discription=?, operator_id=?, number_of_seats=?, tour_status=?" +
                            " WHERE tour_id=?";
    private static final String SQL_GET_ALL =
                            "SELECT tour_id, beg_date, end_date, type_id, resort_id, " +
                            "cost, discription, operator_id, number_of_seats, tour_status" +
                            " FROM tour";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM tour WHERE tour_id=?";

    private TourDAOImpl() {
    }

    public static TourDAOImpl getInstance() {
        return instance;
    }

    @Override
    public void delete(long id) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not delete tour by id", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ArrayList<Tour> getAll() throws DAOException {
        TourTypeDAO tourTypeDAO = TourTypeDAOImpl.getInstance();
        ResortDAO resortDAO = ResortDAOImpl.getInstance();
        TourOperatorDAO tourOperatorDao = TourOperatorDaoImpl.getInstance();
        ArrayList<Tour> tours = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long tourId = resultSet.getLong("tour_id");
                    Date begDate = resultSet.getDate("beg_date");
                    Date endDate = resultSet.getDate("end_date");
                    TourType tourType = tourTypeDAO.getById(resultSet.getLong("type_id"));
                    Resort resort = resortDAO.getById(resultSet.getLong("resort_id"));
                    double cost = resultSet.getDouble("cost");
                    String discription = resultSet.getString("discription");
                    TourOperator operator = tourOperatorDao.getById(resultSet.getLong("operator_id"));
                    int numberOfSeats = resultSet.getInt("number_of_seats");
                    int tourStatus = resultSet.getInt("tour_status");
                    tours.add(new Tour(tourId, begDate, endDate, tourType, resort, cost,
                            discription, operator, numberOfSeats, tourStatus));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find tour by country", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tours;
    }

    @Override
    public void update(Tour item) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TOUR)) {
            preparedStatement.setDate(1, item.getBegDate());
            preparedStatement.setDate(2, item.getEndDate());
            preparedStatement.setLong(3, item.getTourType().getTypeId());
            preparedStatement.setLong(4, item.getResort().getResortId());
            preparedStatement.setDouble(5, item.getCost());
            preparedStatement.setString(6, item.getDiscription());
            preparedStatement.setLong(7, item.getTourOperator().getOperatorId());
            preparedStatement.setInt(8, item.getNumberOfSeats());
            preparedStatement.setInt(9, item.getTourStatus());
            preparedStatement.setLong(10, item.getTourId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update tour ", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void create(Tour item) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TOUR)) {
            preparedStatement.setDate(1, item.getBegDate());
            preparedStatement.setDate(2, item.getEndDate());
            preparedStatement.setLong(3, item.getTourType().getTypeId());
            preparedStatement.setLong(4, item.getResort().getResortId());
            preparedStatement.setDouble(5, item.getCost());
            preparedStatement.setString(6, item.getDiscription());
            preparedStatement.setLong(7, item.getTourOperator().getOperatorId());
            preparedStatement.setInt(8, item.getNumberOfSeats());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not create tour", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Tour getById(long id) throws DAOException {
        TourTypeDAO tourTypeDAO = TourTypeDAOImpl.getInstance();
        ResortDAO resortDAO = ResortDAOImpl.getInstance();
        TourOperatorDAO tourOperatorDao = TourOperatorDaoImpl.getInstance();
        Tour tour = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TOUR_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long tourId = resultSet.getLong("tour_id");
                    Date begDate = resultSet.getDate("beg_date");
                    Date endDate = resultSet.getDate("end_date");
                    TourType tourType = tourTypeDAO.getById(resultSet.getLong("type_id"));
                    Resort resort = resortDAO.getById(resultSet.getLong("resort_id"));
                    double cost = resultSet.getDouble("cost");
                    String discription = resultSet.getString("discription");
                    TourOperator operator = tourOperatorDao.getById(resultSet.getLong("operator_id"));
                    int numberOfSeats = resultSet.getInt("number_of_seats");
                    int tourStatus = resultSet.getInt("tour_status");
                    tour = new Tour(tourId, begDate, endDate, tourType, resort, cost,
                            discription, operator, numberOfSeats, tourStatus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }

        return tour;
    }

    @Override
    public void update(long tourId, int numberOfSeats) throws DAOException {
        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_NUMBER_OF_SEATS)){
            preparedStatement.setInt(1, numberOfSeats);
            preparedStatement.setLong(2, tourId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can not update number of seats in current tour");
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ArrayList<Tour> getToursByCountry(long countryId) throws DAOException {
        TourTypeDAO tourTypeDAO = TourTypeDAOImpl.getInstance();
        ResortDAO resortDAO = ResortDAOImpl.getInstance();
        TourOperatorDAO tourOperatorDao = TourOperatorDaoImpl.getInstance();
        ArrayList<Tour> tours = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TOURS_BY_COUNTRY)) {
            preparedStatement.setLong(1, countryId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long tourId = resultSet.getLong("tour_id");
                    Date begDate = resultSet.getDate("beg_date");
                    Date endDate = resultSet.getDate("end_date");
                    TourType tourType = tourTypeDAO.getById(resultSet.getLong("type_id"));
                    Resort resort = resortDAO.getById(resultSet.getLong("resort_id"));
                    double cost = resultSet.getDouble("cost");
                    String discription = resultSet.getString("discription");
                    TourOperator operator = tourOperatorDao.getById(resultSet.getLong("operator_id"));
                    int numberOfSeats = resultSet.getInt("number_of_seats");
                    int tourStatus = resultSet.getInt("tour_status");
                    tours.add(new Tour(tourId, begDate, endDate, tourType, resort, cost,
                                       discription, operator, numberOfSeats, tourStatus));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not find tour by country", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tours;
    }

    @Override
    public ArrayList<Tour> getToursByResort(long id) throws DAOException {
        TourTypeDAO tourTypeDAO = TourTypeDAOImpl.getInstance();
        ResortDAO resortDAO = ResortDAOImpl.getInstance();
        TourOperatorDAO tourOperatorDao = TourOperatorDaoImpl.getInstance();
        ArrayList<Tour> tours = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TOURS_BY_RESORT)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long tourId = resultSet.getLong("tour_id");
                    Date begDate = resultSet.getDate("beg_date");
                    Date endDate = resultSet.getDate("end_date");
                    TourType tourType = tourTypeDAO.getById(resultSet.getLong("type_id"));
                    Resort resort = resortDAO.getById(resultSet.getLong("resort_id"));
                    double cost = resultSet.getDouble("cost");
                    String discription = resultSet.getString("discription");
                    TourOperator operator = tourOperatorDao.getById(resultSet.getLong("operator_id"));
                    int numberOfSeats = resultSet.getInt("number_of_seats");
                    int tourStatus = resultSet.getInt("tour_status");
                    tours.add(new Tour(tourId, begDate, endDate, tourType, resort, cost,
                            discription, operator, numberOfSeats, tourStatus));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get tour by resort", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tours;
    }

    @Override
    public void delete(Tour item) throws DAOException {

    }

}
