package bitlab.springbootfirstfinal.dto;

import bitlab.springbootfirstfinal.models.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TasksDTO {
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private TaskStatus taskStatus;
    private FoldersDTO folder;
}
