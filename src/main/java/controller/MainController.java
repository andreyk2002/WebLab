package controller;

import dao.*;
import dao.connection.ConnectionPool;
import dao.entity.*;
import logic.AdministratorLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MainController {

    public static void main(String[] args) {
        DaoHelper daoHelper = new DaoHelper(ConnectionPool.getInstance());
        AdministratorLogic logic = new AdministratorLogic(daoHelper);
        ProjectDao projectDao = daoHelper.createProjectDao();
        CustomerDao customerDao = daoHelper.createCustomerDao();
        WorkerDao workerDao = daoHelper.createWorkerDao();
        ProjectDefinitionDao projectDefinitionDao = daoHelper.createProjectDefinitionDao();
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
