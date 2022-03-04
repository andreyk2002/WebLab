package dao.impl.jpa;

import dao.WorkerDao;
import dao.entity.Worker;
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
public class WorkerDaoJpa implements WorkerDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public void addWorker(Worker worker) {
        entityManager.persist(worker);
        entityManager.flush();
    }

    @Override
    public List<Worker> getByTaskId(long projectDefinitionId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> query = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> certificateRoot = query.from(Worker.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("projectDefinitionId"), projectDefinitionId));
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public List<Worker> getFreeWorkers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> query = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> certificateRoot = query.from(Worker.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("isFree"), true));
        return entityManager
                .createQuery(query)
                .getResultList();
    }

    @Override
    public void changeWorkerStatus(List<Worker> workers) {
        for (Worker worker : workers) {
            worker.setFree(false);
            entityManager.merge(worker);
            entityManager.flush();
        }
    }

    @Override
    public void addToProject(List<Worker> workers, long taskId, long projectId) {
        for (Worker worker : workers) {
            worker.setProjectId(projectId);
            worker.setTaskId(taskId);
            entityManager.merge(worker);
            entityManager.flush();
        }
    }

    @Override
    public List<Worker> getByProjectId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> query = criteriaBuilder.createQuery(Worker.class);
        Root<Worker> certificateRoot = query.from(Worker.class);
        query.select(certificateRoot).where(criteriaBuilder.equal(certificateRoot.get("projectId"), id));
        return entityManager
                .createQuery(query)
                .getResultList();
    }
}
