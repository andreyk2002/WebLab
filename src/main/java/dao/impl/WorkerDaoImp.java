package dao.impl;

import dao.AbstractDao;
import dao.WorkerDao;
import dao.entity.Worker;
import dao.mapper.WorkerRowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class WorkerDaoImp extends AbstractDao<Worker> implements WorkerDao {
    private static final String TABLE_NAME = "workers";
    public static final String ADD_WORKER = "INSERT INTO workers (projectId, taskId, surname, name, qualification, salary, isFree) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_BY_TASK_ID = "SELECT * FROM workers WHERE TaskId = ?";
    public static final String FIND_FREE_WORKERS = "SELECT * FROM workers WHERE isFree = true";
    public static final String CHANGE_STATUS = "UPDATE workers SET isFree = 0 WHERE id IN (%s)";
    public static final String ADD_PROJECT = "UPDATE workers SET isFree = 0, projectId = ?, taskId = ? WHERE ID IN (%s)";
    public static final String FIND_WORKERS_BY_PROJECT = "SELECT * FROM workers WHERE projectId = ?";

    public WorkerDaoImp(Connection connection) {
        super(connection, new WorkerRowMapper(), TABLE_NAME);
    }

    @Override
    public void addWorker(Worker worker) {
        updateQuery(ADD_WORKER, -1, 0, worker.getSurname(), worker.getName(),
                worker.getQualification(), worker.getSalary(), true);
    }

    @Override
    public List<Worker> getByTaskId(long taskId) {
        return executeQuery(FIND_BY_TASK_ID, taskId);
    }

    @Override
    public List<Worker> getFreeWorkers() {
        return executeQuery(FIND_FREE_WORKERS);
    }

    @Override
    public void changeWorkerStatus(List<Worker> workers) {
        StringBuilder idString = buildRange(workers);

        String query = String.format(CHANGE_STATUS, idString);
        updateQuery(query);
    }

    @Override
    public void addToProject(List<Worker> workers, long taskId, long projectId) {
        StringBuilder stringBuilder = buildRange(workers);
        String query = String.format(ADD_PROJECT, stringBuilder);
        updateQuery(query, projectId, taskId);
    }

    @Override
    public List<Worker> getByProjectId(long id) {
        return executeQuery(FIND_WORKERS_BY_PROJECT, id);
    }

    private StringBuilder buildRange(List<Worker> workers) {
        List<Long> ids = workers.stream().map(Worker::getId).collect(Collectors.toList());
        StringBuilder idString = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            idString.append(ids.get(i));
            if (i != ids.size() - 1) {
                idString.append(",");
            }
        }
        return idString;
    }


}
