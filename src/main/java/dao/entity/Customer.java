package dao.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Table(name="customers")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Customer {

    private long id;

    private String name;

    private String surname;

    private double money;
}
