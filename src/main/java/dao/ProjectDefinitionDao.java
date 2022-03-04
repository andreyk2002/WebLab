package dao;

import dao.entity.ProjectDefinition;

import java.util.List;
import java.util.Optional;

/**
 * Performs persistence operations on project definition entities
 */
public interface ProjectDefinitionDao {

     List<ProjectDefinition> getByCustomerId(long customerId);

     long createIfAbsent(ProjectDefinition definition);

     long createProjectDefinition(ProjectDefinition definition);

    Optional<ProjectDefinition> getById(long projectDefinitionId);
}
