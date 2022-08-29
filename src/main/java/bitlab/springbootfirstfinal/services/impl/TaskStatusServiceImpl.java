package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.dto.TaskStatusDTO;
import bitlab.springbootfirstfinal.mapper.TaskStatusMapper;
import bitlab.springbootfirstfinal.models.TaskStatus;
import bitlab.springbootfirstfinal.repository.TaskStatusRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.TaskService;
import bitlab.springbootfirstfinal.services.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {
    private final TaskStatusRepository taskStatusRepository;
    private final TasksRepository tasksRepository;
    private final TaskStatusMapper taskStatusMapper;

    @Override
    public List<TaskStatusDTO> allTaskStatus() {
        return taskStatusMapper.toDtoList(taskStatusRepository.findAllByOrderByTaskStatusId());
    }
}
