package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.dto.TasksDTO;
import bitlab.springbootfirstfinal.models.TaskStatus;
import bitlab.springbootfirstfinal.models.Tasks;

import java.util.List;

public interface TaskService {

    TasksDTO task(Long id);
    List<TasksDTO> tasksList(Long id);

    Tasks updateTask(Tasks task, Long folderId);
    void deleteTask(Long id);
    void addTask(Tasks task, Long folderId, TaskStatus taskStatusId);
}
