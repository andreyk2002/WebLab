package dao.mapper;

import dao.RowMapper;
import dao.entity.Qualification;
import dao.entity.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskObjectMapper implements RowMapper<Task> {
    private static final String ID = "ID";
    private static final String PROJECT_DEFINITION_ID = "ProjectDefinitionId";
    public static final String MIN_QUALIFICATION = "MinQualification";
    public static final String WORKERS_COUNT = "WorkersCount";

    @Override
    public Task map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long projectDefinitionId = resultSet.getLong(PROJECT_DEFINITION_ID);
        Qualification minQualification = Qualification.valueOf(resultSet.getString(MIN_QUALIFICATION));
        int workersCount = resultSet.getInt(WORKERS_COUNT);
        return new Task(id, projectDefinitionId, minQualification,  workersCount);
    }
}
