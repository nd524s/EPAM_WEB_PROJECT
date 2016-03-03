package com.epam.agency.dao.impl;

import com.epam.agency.dao.GenericDAO;
import com.epam.agency.dao.TourOperatorDAO;
import com.epam.agency.domain.TourOperator;
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
public class TourOperatorDaoImpl implements TourOperatorDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final TourOperatorDaoImpl instance = new TourOperatorDaoImpl();
    private static final String SQL_GET_OPERATOR_BY_ID = "SELECT operator_id, operator_name," +
                                                         " tel_number, e_mail FROM tour_operator" +
                                                         " WHERE operator_id=?";
    private static final String SQL_GET_OPERATORS = "SELECT operator_id, operator_name," +
                                                    " tel_number, e_mail FROM tour_operator";

    private TourOperatorDaoImpl() {
    }

    public static TourOperatorDaoImpl getInstance() {
        return instance;
    }

    @Override
    public ArrayList<TourOperator> getAll() throws DAOException {
        ArrayList<TourOperator> operators = new ArrayList<>();

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_OPERATORS)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long operatorId = resultSet.getLong("operator_id");
                    String operatorName = resultSet.getString("operator_name");
                    String telNumber = resultSet.getString("tel_number");
                    String email = resultSet.getString("e_mail");
                    operators.add(new TourOperator(operatorId,operatorName, telNumber, email));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get all tour operators", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return operators;
    }

    @Override
    public TourOperator getById(long id) throws DAOException {
        TourOperator tourOperator = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_OPERATOR_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long operatorId = resultSet.getLong("operator_id");
                    String operatorName = resultSet.getString("operator_name");
                    String telNumber = resultSet.getString("tel_number");
                    String email = resultSet.getString("e_mail");
                    tourOperator = new TourOperator(operatorId, operatorName, telNumber, email);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get tour operator by ID", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return tourOperator;
    }

    @Override
    public void create(TourOperator item) throws DAOException {

    }

    @Override
    public void delete(TourOperator item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(TourOperator item) {

    }
}
