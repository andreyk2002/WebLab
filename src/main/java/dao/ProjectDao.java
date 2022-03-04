package dao;

import dao.entity.Project;
import dao.entity.ProjectDefinition;
import dao.entity.Worker;

import java.util.List;

/**
 * Performs persistence operations on project entities
 */
public interface ProjectDao {

     List<Project> getUnpaidProjects();

     void payForProject(long projectId);

     long createProject(Project project);

     List<Project> getByCustomerId(long customer);
}
