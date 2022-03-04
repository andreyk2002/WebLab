package dao.impl;

import dao.AbstractDao;
import dao.ProjectDao;
import dao.entity.Project;
import dao.mapper.ProjectRowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDaoImpl extends AbstractDao<Project> implements ProjectDao {
    private static final String TABLE_NAME = "projects";
    private static final String SELECT_INPAID_PROJECTS = "SELECT * From projects where isPaidByCustomer=false";
    public static final String PAY_QUERY = "UPDATE projects SET IsPaidByCustomer=true WHERE ID = ?";
    public static final String CREATE_PROJECT = "INSERT into projects (ProjectName, ProjectDefinitionId, CustomerId) VALUES (?, ?, ?)";
    public static final String FIND_BY_CUSTOMER = "SELECT * FROM projects WHERE customerId = ?";

    public ProjectDaoImpl(Connection connection) {
        super(connection, new ProjectRowMapper(), TABLE_NAME);
    }


    @Override
    public List<Project> getUnpaidProjects() {
        return executeQuery(SELECT_INPAID_PROJECTS);
    }

    @Override
    public void payForProject(long projectId) {
        updateQuery(PAY_QUERY, projectId);
    }

    @Override
    public long createProject(Project project) {
        return updateQueryAndGetID(CREATE_PROJECT, project.getProjectName(), project.getProjectDefinitionId(), project.getCustomerId());
    }

    @Override
    public List<Project> getByCustomerId(long customerId) {
        return executeQuery(FIND_BY_CUSTOMER, customerId);
    }
}
