package dao.mapper;

import dao.RowMapper;
import dao.entity.Project;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProjectRowMapper implements RowMapper<Project> {
    private static final String ID = "ID";
    private static final String NAME = "ProjectName";
    private static final String PAID_BY_CUSTOMER = "IsPaidByCustomer";
    private static final String PROJECT_DEFINITION_ID = "ProjectDefinitionId";
    public static final String CUSTOMER_ID = "CustomerId";

    @Override
    public Project map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        boolean paidByCustomer = resultSet.getBoolean(PAID_BY_CUSTOMER);
        long projectDefId = resultSet.getLong(PROJECT_DEFINITION_ID);
        long customerId = resultSet.getLong(CUSTOMER_ID);
        return new Project(id, projectDefId, name, customerId, paidByCustomer);
    }
}
