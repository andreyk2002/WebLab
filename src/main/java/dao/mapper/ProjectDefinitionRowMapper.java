package dao.mapper;

import dao.RowMapper;
import dao.entity.ProjectDefinition;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProjectDefinitionRowMapper implements RowMapper<ProjectDefinition> {
    private static final String ID = "ID";
    private static final String CUSTOMER_ID = "CustomerId";
    public static final String DESCRIPTION = "Description";

    @Override
    public ProjectDefinition map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long customerId = resultSet.getLong(CUSTOMER_ID);
        String description = resultSet.getString(DESCRIPTION);
        return new ProjectDefinition(id, customerId, description);
    }
}
