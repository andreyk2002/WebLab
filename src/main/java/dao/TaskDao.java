package dao;

import dao.entity.Task;

import java.util.List;
/**
 * Performs persistence operations on task entities
 */
public interface TaskDao {

    List<Task>getByProjectDefinitionId(long projectDefinitionId);
}
