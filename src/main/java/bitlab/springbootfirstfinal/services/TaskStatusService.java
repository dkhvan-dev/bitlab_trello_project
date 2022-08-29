package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.dto.TaskStatusDTO;
import bitlab.springbootfirstfinal.models.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatusDTO> allTaskStatus();

}
