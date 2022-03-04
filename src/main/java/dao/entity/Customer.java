package dao.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@NamedQuery(name="Customer.findById", query = "SELECT c FROM Customer c where c.id = :id")
public class Customer {
    @Id
    private long id;

    private String name;

    private String surname;

    private double money;
}
