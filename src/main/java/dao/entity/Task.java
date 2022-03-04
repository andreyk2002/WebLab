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
@Table(name="tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@NamedQuery(name = "Customer.findById", query = "SELECT t FROM Task t where t.id = :id")
@NamedQuery(name = "Customer.findByProjectDefinitionId", query = "SELECT t FROM Task t where t.projectDefinitionId = :pdid")
public class Task {
    @Id
    private long id;
    private long projectDefinitionId;
    private Qualification minQualification;
    private int workersCount;
}
