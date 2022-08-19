package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TaskCategoriesRepository;
import bitlab.springbootfirstfinal.services.TaskCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<TaskCategories> detailsTaskCategories(Long id) {
        Folders folder = foldersRepository.findById(id).orElse(null);
        List<TaskCategories> taskCategories = new ArrayList<>();
        if (folder != null) {
            return taskCategories = folder.getCategories();
        }
        return null;
    }

    @Override
    public void addCategory(Long folderId, Long categoryId) {
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        TaskCategories taskCategory = taskCategoriesRepository.findById(categoryId).orElse(null);
        if (folder != null && taskCategory != null && !folder.getCategories().contains(taskCategory)) {
            List<TaskCategories> taskCategories = folder.getCategories();
            taskCategories.add(taskCategory);
            folder.setCategories(taskCategories);
            foldersRepository.save(folder);
        } else {
            List<TaskCategories> taskCategories = new ArrayList<>();
        }
    }

    @Override
    public void deleteCategory(Long folderId, Long categoryId) {
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        TaskCategories taskCategory = taskCategoriesRepository.findById(categoryId).orElse(null);
        List<TaskCategories> allCategories = taskCategoriesRepository.findAllByOrderByCategoriesIdAsc();
        if (folder != null && taskCategory != null) {
            for (int i = 0; i < allCategories.size(); i++) {
                if (allCategories.get(i).getCategoriesId().equals(categoryId)) {
                    folder.getCategories().remove(i);
                    foldersRepository.save(folder);
                    break;
                }
            }
        }
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
