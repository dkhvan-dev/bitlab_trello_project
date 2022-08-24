package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;

import java.util.List;

public interface TaskCategoriesService {
    List<TaskCategories> allCategories();
    void addToListCategory(TaskCategories taskCategory);
    void deleteFromListCategory(Long categoryId);
    void editCategory(TaskCategories taskCategory);
}
