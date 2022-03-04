package logic;

import dao.CustomerDao;
import dao.ProjectDao;
import dao.ProjectDefinitionDao;
import dao.TaskDao;
import dao.WorkerDao;
import dao.entity.Bill;
import dao.entity.Customer;
import dao.entity.Project;
import dao.entity.ProjectDefinition;
import dao.entity.Task;
import dao.entity.Worker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AdministratorLogicV2 {
    private ProjectDefinitionDao projectDefinitionDao;
    private WorkerDao workerDao;
    private ProjectDao projectDao;
    private TaskDao taskDao;
    private CustomerDao customerDao;

    public Project createProject(Customer customer, String projectName, ProjectDefinition definition, List<Task> tasks) {
        long defId = projectDefinitionDao.createIfAbsent(definition);
        List<Worker> workers = new ArrayList<>();
        Project project = new Project(0, defId, projectName, customer.getId(), false);
        long projectId = projectDao.createProject(project);
        addWorkersToTasks(tasks, workers, workerDao, projectId);
        workers.forEach(worker -> worker.setProjectId(projectId));
        return new Project(projectId, defId, projectName, customer.getId(), false);
    }

    public List<Worker> addWorkersToProject(Project existingProject){
        Optional<ProjectDefinition> optionalProjectDefinition = projectDefinitionDao.getById(existingProject.getProjectDefinitionId());
        optionalProjectDefinition.orElseThrow(() -> new RuntimeException("Cannot find projectDefinition for project id = " + existingProject.getId()));
        ProjectDefinition definition = optionalProjectDefinition.get();
        List<Worker> workers = new ArrayList<>();
        List<Task> tasks = taskDao.getByProjectDefinitionId(definition.getId());
        addWorkersToTasks(tasks, workers, workerDao, existingProject.getId());
        workers.forEach(worker -> worker.setProjectId(existingProject.getId()));
        return workers;
    }

    private void addWorkersToTasks(List<Task> tasks, List<Worker> workers, WorkerDao workerDao, long projectId) {
        for (Task task : tasks) {
            List<Worker> taskWorkers = getWorkers(workerDao, task);
            workers.addAll(taskWorkers);
            workers.forEach(worker -> worker.setFree(false));
            workers.forEach(worker -> worker.setTaskId(task.getId()));
            workerDao.addToProject(workers, task.getId(), projectId);
        }
    }

    public Bill createBill(Project project) {
        List<Worker> workers = workerDao.getByProjectId(project.getId());
        double result = workers.stream()
                .reduce(0., (partialAgeResult, user) -> partialAgeResult + user.getSalary(), Double::sum);
        return new Bill(result, workers, project.getId());
    }

    public Customer pay(Bill bill, Customer customer) {
        if (bill.getTotalPrice() > customer.getMoney()) {
            throw new RuntimeException("not enough money to pay for the project");
        }
        customer.setMoney(customer.getMoney() - bill.getTotalPrice());
        customerDao.changeMoney(customer.getId(), customer.getMoney());
        projectDao.payForProject(bill.getProjectId());
        return customer;
    }

    private List<Worker> getWorkers(WorkerDao workerDao, Task task) {
        List<Worker> freeWorkers = workerDao.getFreeWorkers();
        List<Worker> workers = freeWorkers.stream()
                .filter(worker -> task.getMinQualification().getValue() < worker.getQualification().getValue())
                .sorted(Comparator.comparingInt(worker -> worker.getQualification().getValue()))
                .limit(task.getWorkersCount())
                .collect(Collectors.toList());
        workerDao.changeWorkerStatus(workers);
        return workers;
    }
}
