package dao;

import dao.connection.ConnectionPool;
import dao.connection.ProxyConnection;
import dao.impl.*;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class DaoHelper implements AutoCloseable {

    private final ProxyConnection connection;

    public DaoHelper(ConnectionPool pool) {
        this.connection = pool.getConnection();
    }

    public ProjectDao createProjectDao(){
        return new ProjectDaoImpl(connection);
    }

    public WorkerDao createWorkerDao() {
        return new WorkerDaoImp(connection);
    }

    public ProjectDefinitionDao createProjectDefinitionDao() {
        return new ProjectDefinitionDaoImpl(connection);
    }

    public CustomerDao createCustomerDao() {
        return new CustomerDaoImpl(connection);
    }

    public TaskDao createTaskDao() {
        return new TaskDaoImpl(connection);
    }

    @Override
    public void close() {
        connection.close();
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }

    public void endTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}
