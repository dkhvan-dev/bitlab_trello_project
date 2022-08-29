package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TaskCategoriesRepository;
import bitlab.springbootfirstfinal.services.TaskCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskCategoriesImpl implements TaskCategoriesService {
    @Autowired
    FoldersRepository foldersRepository;

    @Autowired
    TaskCategoriesRepository taskCategoriesRepository;

    @Override
    public List<TaskCategories> allCategories() {
        return taskCategoriesRepository.findAllByOrderByCategoriesIdAsc();
    }

    @Override
    public void addToListCategory(TaskCategories taskCategory) {
        taskCategoriesRepository.save(taskCategory);
    }

    @Override
    public void deleteFromListCategory(Long categoryId) {
        TaskCategories taskCategory = taskCategoriesRepository.findById(categoryId).orElse(null);
        List<Folders> folders = foldersRepository.findAll();

        if (taskCategory != null) {
            folders.forEach(folder -> {
                if (folder.getCategories().contains(taskCategory)) {
                    folder.getCategories().remove(taskCategory);
                    foldersRepository.save(folder);
                }
            });
            taskCategoriesRepository.deleteById(categoryId);
        }
    }

    @Override
    public void editCategory(TaskCategories taskCategory) {
        if (taskCategory != null) {
            taskCategoriesRepository.save(taskCategory);
        }
    }
}
