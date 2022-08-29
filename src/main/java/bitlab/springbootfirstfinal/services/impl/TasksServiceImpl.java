package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.dto.TasksDTO;
import bitlab.springbootfirstfinal.mapper.TasksMapper;
import bitlab.springbootfirstfinal.models.Comments;
import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskStatus;
import bitlab.springbootfirstfinal.models.Tasks;
import bitlab.springbootfirstfinal.repository.CommentsRepository;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TaskService {
    private final TasksRepository tasksRepository;
    private final FoldersRepository foldersRepository;
    private final CommentsRepository commentsRepository;
    private final TasksMapper tasksMapper;

    @Override
    public TasksDTO task(Long id) {
        return tasksMapper.toDto(tasksRepository.findById(id).orElse(null));
    }

    @Override
    public List<TasksDTO> tasksList(Long id) {
        return tasksMapper.toDtoList(tasksRepository.searchAllByFolder_FolderIdOrderByTaskId(id));
    }

    @Override
    public Tasks updateTask(Tasks task, Long folderId) {
        if (task != null) {
            task.setFolder(foldersRepository.findById(folderId).orElse(null));
            return tasksRepository.save(task);
        }
        return null;
    }

    @Override
    public void deleteTask(Long id) {
        List<Comments> comments = commentsRepository.searchAllByTask_TaskIdOrderByCommentsIdDesc(id);
        commentsRepository.deleteAll(comments);
        tasksRepository.deleteById(id);
    }

    @Override
    public void addTask(Tasks task, Long folderId, TaskStatus taskStatusId) {
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        if (task != null && folder != null) {
            task.setFolder(folder);
            task.setTaskStatus(taskStatusId);
            tasksRepository.save(task);
        }
    }
}
