package dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NamedQuery(name="Customer.findById", query = "SELECT w FROM Worker w where w.id = :id")
@NamedQuery(name="Customer.findProject", query = "SELECT w FROM Worker w where w.projectId = :projectId")
@NamedQuery(name="Customer.findTask", query = "SELECT w FROM Worker w where w.taskId = :taskId")
@NamedQuery(name="Customer.findFree", query = "SELECT w FROM Worker w where w.isFree")
public class Worker {
    @Id
    private long id;
    private long projectId;
    private long taskId;
    private String surname;
    private String name;
    private Qualification qualification;
    private double salary;
    private boolean isFree;
}
