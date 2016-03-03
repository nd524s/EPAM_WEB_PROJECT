package com.epam.agency.dao.impl;

import com.epam.agency.dao.RoleDAO;
import com.epam.agency.domain.Role;
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
 * Created by Никита on 08.02.2016.
 */
public class RoleDAOImpl implements RoleDAO {
    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final RoleDAOImpl instance = new RoleDAOImpl();
    private static final String SQL_GET_ROLE_BY_ID = "SELECT role_id, role_name FROM role" +
                                                     " WHERE role_id=?";

    private RoleDAOImpl() {
    }

    public static RoleDAOImpl getInstance() {
        return instance;
    }

    @Override
    public Role getById(long id) throws DAOException {
        Role role = null;

        try(ProxyConnection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ROLE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long roleId = resultSet.getLong("role_id");
                    String roleName = resultSet.getString("role_name");
                    role = new Role(roleId,roleName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can not get role by id", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
        return role;
    }

    @Override
    public ArrayList<Role> getAll() throws DAOException {
        return null;
    }

    @Override
    public void update(Role item) {

    }

    @Override
    public void create(Role item) throws DAOException {

    }

    @Override
    public void delete(Role item) throws DAOException {

    }

    @Override
    public void delete(long id) {

    }
}
