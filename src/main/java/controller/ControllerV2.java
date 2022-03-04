package controller;

import dao.CustomerDao;
import dao.ProjectDao;
import dao.ProjectDefinitionDao;
import dao.WorkerDao;
import dao.entity.Bill;
import dao.entity.Customer;
import dao.entity.Project;
import dao.entity.ProjectDefinition;
import dao.entity.Qualification;
import dao.entity.Task;
import dao.entity.Worker;
import logic.AdministratorLogicV2;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

@Controller
@AllArgsConstructor
public class ControllerV2 {
    private AdministratorLogicV2 logic;
    private ProjectDefinitionDao projectDefinitionDao;
    private WorkerDao workerDao;
    private ProjectDao projectDao;
    private CustomerDao customerDao;

    public void process() {
        String command = "";
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Customer current = customerDao.getById(random.nextInt(50)).get();
        do {
            System.out.println("Enter command:");
            command = scanner.nextLine();
            if ("GetTaskByCustomerId".equalsIgnoreCase(command)) {
                System.out.println("Enter customer id:");
                long id = scanner.nextLong();
                scanner.nextLine();
                List<ProjectDefinition> tasks = projectDefinitionDao.getByCustomerId(id);
                tasks.forEach(System.out::println);
            } else if ("GetWorkerByTaskId".equalsIgnoreCase(command)) {
                System.out.println("Enter task id:");
                long id = scanner.nextLong();
                scanner.nextLine();
                List<Worker> workers = workerDao.getByTaskId(id);
                workers.forEach(System.out::println);
            } else if ("AddWorkersToProject".equalsIgnoreCase(command)) {
                List<Project> projects = projectDao.getByCustomerId(current.getId());
                if (projects.size() == 0) {
                    System.out.println("No projects for current customer");
                    continue;
                }
                Project randomProject = projects.get(random.nextInt(projects.size()));
                if (randomProject.isPaidByCustomer()) {
                    System.out.println("Project already has all required workers");
                    continue;
                } else {
                    List<Worker> workers = logic.addWorkersToProject(randomProject);
                    workers.forEach(System.out::println);
                    System.out.println("Workers were successfully added to the project = " + randomProject);
                }
                Bill bill = logic.createBill(randomProject);
                System.out.println("Bill for the workers = " + bill);

                Customer pay = logic.pay(bill, current);
                System.out.println("Payment handled successfully. Customer = " + pay);
            } else if ("GetUnpaidProjects".equalsIgnoreCase(command)) {
                List<Project> unpaidProjects = projectDao.getUnpaidProjects();
                unpaidProjects.forEach(System.out::println);
            } else if ("CreateProject".equalsIgnoreCase(command)) {
                ProjectDefinition definition = new ProjectDefinition(0, current.getId(), "definition for project");
                List<Task> tasks = List.of(new Task(0, definition.getId(), Qualification.MIDDLE, 3));
                Project project = logic.createProject(current, "project", definition, tasks);
                System.out.println(project);
            }

        } while (!Objects.equals(command, "exit"));
    }
}
