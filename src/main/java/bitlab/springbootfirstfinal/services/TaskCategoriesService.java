package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.TaskCategories;

import java.util.List;

public interface TaskCategoriesService {
    List<TaskCategories> allCategories();
    List<TaskCategories> detailsTaskCategories(Long id);
    void addCategory(Long folderId, Long categoryId);
    void deleteCategory(Long folderId, Long categoryId);
    void addToListCategory(TaskCategories taskCategory);
    void deleteFromListCategory(Long categoryId);
    void editCategory(TaskCategories taskCategory);
}
