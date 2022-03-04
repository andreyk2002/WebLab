package dao.impl;

import dao.AbstractDao;
import dao.ProjectDefinitionDao;
import dao.RowMapper;
import dao.entity.ProjectDefinition;
import dao.mapper.ProjectDefinitionRowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ProjectDefinitionDaoImpl extends AbstractDao<ProjectDefinition>  implements ProjectDefinitionDao {
    private static final String TABLE_NAME = "project_definitions";
    public static final String CREATE_PROJECT_DEFINITION = "INSERT IN project_definintions (Description, CustomerId) VALUES (?, ?)";
    public static final String FIND_BY_CUSTOMER_ID = "SELECT * FROM project_definitions WHERE CustomerId = ?";

    public ProjectDefinitionDaoImpl(Connection connection) {
        super(connection, new ProjectDefinitionRowMapper(), TABLE_NAME);
    }

    @Override
    public List<ProjectDefinition> getByCustomerId(long customerId) {
        return executeQuery(FIND_BY_CUSTOMER_ID, customerId);
    }

    @Override
    public long createIfAbsent(ProjectDefinition definition) {
        Optional<ProjectDefinition> projectDefinition = getById(definition.getId());
        if(projectDefinition.isEmpty()){
           return createProjectDefinition(definition);
        }
        return projectDefinition.get().getId();
    }

    @Override
    public long createProjectDefinition(ProjectDefinition definition) {
       return updateQueryAndGetID(CREATE_PROJECT_DEFINITION, definition.getDescription(), definition.getCustomerId());
    }
}
