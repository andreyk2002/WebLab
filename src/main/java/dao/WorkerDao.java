package dao;

import dao.entity.Worker;

import java.util.List;
/**
 * Performs persistence operations on worker entities
 */
public interface WorkerDao {

    void addWorker(Worker worker);

    List<Worker> getByTaskId(long projectDefinitionId);

    List<Worker> getFreeWorkers();

    void changeWorkerStatus(List<Worker>workers);

    void addToProject(List<Worker> workers, long taskId, long projectId);

    List<Worker> getByProjectId(long id);
}
