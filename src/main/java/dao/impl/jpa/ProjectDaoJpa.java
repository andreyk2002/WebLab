package dao.impl.jpa;

import dao.ProjectDao;
import dao.entity.Project;
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
public class ProjectDaoJpa implements ProjectDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Project> getUnpaidProjects() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = criteriaBuilder.createQuery(Project.class);
        Root<Project> certificateRoot = query.from(Project.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("isPaidByCustomer"), false));
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public void payForProject(long projectId) {
        Project project = entityManager.find(Project.class, projectId);
        project.setPaidByCustomer(true);
        entityManager.merge(project);
        entityManager.flush();
    }

    @Override
    public long createProject(Project project) {
        entityManager.persist(project);
        entityManager.flush();
        return project.getId();
    }

    @Override
    public List<Project> getByCustomerId(long customer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> query = criteriaBuilder.createQuery(Project.class);
        Root<Project> certificateRoot = query.from(Project.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("customerId"), customer));
        return entityManager
                .createQuery(query)
                .getResultList();
    }
}
