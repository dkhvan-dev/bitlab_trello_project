package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskStatus;
import bitlab.springbootfirstfinal.models.Tasks;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksServiceImpl implements TaskService {
    @Autowired
    TasksRepository tasksRepository;

    @Autowired
    FoldersRepository foldersRepository;

    @Override
    public Tasks task(Long id) {
        return tasksRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tasks> tasksList(Long id) {
        return tasksRepository.searchAllByFolder_FolderIdOrderByTaskId(id);
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
