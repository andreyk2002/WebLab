package dao.mapper;

import dao.RowMapper;
import dao.entity.Qualification;
import dao.entity.Worker;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;


public class WorkerRowMapper implements RowMapper<Worker> {
    private static final String ID = "ID";
    private static final String NAME = "Name";
    private static final String SURNAME = "Surname";
    private static final String PROJECT_ID = "ProjectId";
    private static final String PROJECT_QUALIFICATION = "Qualification";
    private static final String SALARY = "Salary";
    private static final String IS_FREE = "IsFree";
    private static final String TASK_ID = "TaskId";

    @Override
    public Worker map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String name = resultSet.getString(NAME);
        String surname = resultSet.getString(SURNAME);
        long projectId = resultSet.getLong(PROJECT_ID);
        long taskId = resultSet.getLong(TASK_ID);
        String qualification = resultSet.getString(PROJECT_QUALIFICATION);
        Qualification q = Qualification.valueOf(qualification.toUpperCase(Locale.ROOT));
        double salary = resultSet.getDouble(SALARY);
        boolean isFree = resultSet.getBoolean(IS_FREE);
        return new Worker(id, projectId, taskId, surname, name, q, salary, isFree);
    }
}
