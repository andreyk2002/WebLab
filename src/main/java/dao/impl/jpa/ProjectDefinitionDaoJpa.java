package dao.impl.jpa;

import dao.ProjectDefinitionDao;
import dao.entity.ProjectDefinition;
import dao.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProjectDefinitionDaoJpa implements ProjectDefinitionDao {
    @PersistenceContext
    private final EntityManager entityManager;


    @Override
    public List<ProjectDefinition> getByCustomerId(long customerId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProjectDefinition> query = criteriaBuilder.createQuery(ProjectDefinition.class);
        Root<ProjectDefinition> certificateRoot = query.from(ProjectDefinition.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("customerId"), customerId));
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public long createIfAbsent(ProjectDefinition definition) {
        entityManager.merge(definition);
        entityManager.flush();
        return definition.getId();
    }

    @Override
    public long createProjectDefinition(ProjectDefinition definition) {
        entityManager.persist(definition);
        entityManager.flush();
        return definition.getId();
    }

    @Override
    public Optional<ProjectDefinition> getById(long projectDefinitionId) {
        ProjectDefinition projectDefinition = entityManager.find(ProjectDefinition.class, projectDefinitionId);
        return Optional.of(projectDefinition);
    }
}
