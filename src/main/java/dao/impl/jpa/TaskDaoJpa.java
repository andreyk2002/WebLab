package dao.impl.jpa;

import dao.TaskDao;
import dao.entity.Project;
import dao.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskDaoJpa implements TaskDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Task> getByProjectDefinitionId(long projectDefinitionId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> query = criteriaBuilder.createQuery(Task.class);
        Root<Task> certificateRoot = query.from(Task.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("projectDefinitionId"), projectDefinitionId));
        return entityManager
                .createQuery(query)
                .getResultList();
    }
}
