package dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "projectDefinitions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NamedQuery(name = "Customer.findById", query = "SELECT p FROM ProjectDefinition p where p.id = :id")
@NamedQuery(name = "Customer.findByCustomerId", query = "SELECT p FROM ProjectDefinition p where p.customerId = :customerId")
public class ProjectDefinition {
    @Id
    private long id;
    private long customerId;
    private String description;
}
