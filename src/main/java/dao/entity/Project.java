package dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Project {

    private long id;
    private long projectDefinitionId;
    private String projectName;
    private long customerId;
    private boolean paidByCustomer;
}
