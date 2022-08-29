package bitlab.springbootfirstfinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusDTO {
    private Long taskStatusId;
    private String statusName;
    private String statusColor;
}
