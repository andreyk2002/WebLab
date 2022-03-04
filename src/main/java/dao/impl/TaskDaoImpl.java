package dao.impl;

import dao.AbstractDao;
import dao.TaskDao;
import dao.connection.ProxyConnection;
import dao.entity.Task;
import dao.mapper.TaskObjectMapper;

import java.sql.Connection;
import java.util.List;

public class TaskDaoImpl  extends AbstractDao<Task> implements TaskDao{
    private static final String TABLE_NAME = "tasks";

    public TaskDaoImpl(Connection connection) {
        super(connection, new TaskObjectMapper(), TABLE_NAME);
    }

    @Override
    public List<Task> getByProjectDefinitionId(long projectDefinitionId) {
        return executeQuery("SELECT * FROM tasks WHERE projectDefinitionId = ?", projectDefinitionId);
    }
}
