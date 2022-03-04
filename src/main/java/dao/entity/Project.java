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
import java.util.List;

@Entity
@Table(name="projects")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@NamedQuery(name="Customer.findById", query = "SELECT p FROM Project p where p.id = :id")
@NamedQuery(name="Customer.findByCustomerId", query = "SELECT p FROM Project p where p.customerId = :customerId")
@NamedQuery(name="Customer.findByProjectDefinition", query = "SELECT p FROM Project p where p.projectDefinitionId = :pdid")
@NamedQuery(name="Customer.findPaid", query = "SELECT p FROM Project p where p.paidByCustomer")
public class Project {

    @Id
    private long id;
    private long projectDefinitionId;
    private String projectName;
    private long customerId;
    private boolean paidByCustomer;
}
