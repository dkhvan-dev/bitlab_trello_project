package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;

import java.util.List;

public interface FoldersService {
    List<Folders> allFolders();
    Folders addFolder(Folders folder);
    Folders detailsFolder(Long id);
    void deleteFolder(Long folderId);
    Folders editFolderTitle(Long folderId, String folderTitle);
    List<TaskCategories> detailsTaskCategories(Long id);
    void addCategoryToFolder(Long folderId, Long categoryId);
    void deleteCategoryFromFolder(Long folderId, Long categoryId);
}
