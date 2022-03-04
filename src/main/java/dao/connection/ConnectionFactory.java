package dao.connection;

import com.mysql.cj.jdbc.Driver;
import dao.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionFactory {
    public static final String BUNDLE_NAME = "database";
    private final String url;
    private final Properties properties;

    public ConnectionFactory()  {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
        ResourceBundle resource = ResourceBundle.getBundle(BUNDLE_NAME);
        url = resource.getString("url");
        properties = getProperties(resource);
    }

    public Connection create()  {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private Properties getProperties(ResourceBundle resource) {
        String user = resource.getString("user");
        String password = resource.getString("password");
        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", password);
        return properties;
    }

}
