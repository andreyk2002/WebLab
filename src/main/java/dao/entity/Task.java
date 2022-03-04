package dao.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Task {
    private long id;
    private long projectDefinitionId;
    private Qualification minQualification;
    private int workersCount;
}
