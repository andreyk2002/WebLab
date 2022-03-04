package dao.entity;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Worker {
    private long id;
    private long projectId;
    private long taskId;
    private String surname;
    private String name;
    private Qualification qualification;
    private double salary;
    private boolean isFree;
}
