package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.TaskStatus;
import bitlab.springbootfirstfinal.repository.TaskStatusRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.TaskService;
import bitlab.springbootfirstfinal.services.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    @Autowired
    TaskStatusRepository taskStatusRepository;

    @Autowired
    TasksRepository tasksRepository;

    @Override
    public List<TaskStatus> allTaskStatus() {
        return taskStatusRepository.findAllByOrderByTaskStatusId();
    }
}
