package dao.connection;

import dao.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static final int CONNECTIONS_ALLOWED = 10;

    private final Semaphore connectionSemaphore = new Semaphore(CONNECTIONS_ALLOWED);
    private final Queue<ProxyConnection> availableConnections;
    private final Queue<ProxyConnection> connectionsInUse;
    private final ConnectionFactory connectionFactory;

    private final static AtomicReference<ConnectionPool> INSTANCE = new AtomicReference<>();
    private final static Lock LOCK = new ReentrantLock();


    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = INSTANCE.get();
        if (localInstance == null) {
            try {
                LOCK.lock();
                localInstance = INSTANCE.get();
                if (localInstance == null) {
                    ConnectionPool pool = new ConnectionPool();
                    INSTANCE.getAndSet(pool);
                }
            } catch (DaoException e) {
                LOGGER.error("Unexpected exception in connection pool:" + e.getMessage());
                throw new ConnectionPoolException(e.getMessage(), e);
            } finally {
                LOCK.unlock();
            }
        }
        return INSTANCE.get();
    }

    private ConnectionPool() throws DaoException {
        connectionFactory = new ConnectionFactory();
        connectionsInUse = new ArrayDeque<>();
        availableConnections = new ArrayDeque<>();
        addConnections();
    }

    private void addConnections() throws DaoException {
        for (int i = 0; i < CONNECTIONS_ALLOWED; i++) {
            Connection connection = connectionFactory.create();
            ProxyConnection proxyConnection = new ProxyConnection(connection, this);
            availableConnections.add(proxyConnection);
        }
    }

    public void returnConnection(ProxyConnection connection) {
        try {
            LOCK.lock();
            if (connectionsInUse.contains(connection)) {
                availableConnections.offer(connection);
                connectionsInUse.remove(connection);
                connectionSemaphore.release();
            }
        } finally {
            LOCK.unlock();
        }
    }

    public ProxyConnection getConnection() {
        try {
            connectionSemaphore.acquire();
            LOCK.lock();
            ProxyConnection connection = availableConnections.poll();
            connectionsInUse.add(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            LOCK.unlock();
        }
    }

    public void closeAll() {
        for (ProxyConnection proxyConnection : connectionsInUse) {
            Connection connection = proxyConnection.getConnection();
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Unexpected exception in connection pool:" + e.getMessage());
                throw new ConnectionPoolException(e.getMessage(), e);
            }
        }
    }
}
