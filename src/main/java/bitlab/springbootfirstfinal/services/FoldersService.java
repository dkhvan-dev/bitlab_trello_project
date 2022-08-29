package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;

import java.util.List;

public interface FoldersService {
    List<Folders> allFolders(Long currentUserId);
    Folders addFolder(Folders folder, Long currentUserId);
    Folders detailsFolder(Long id, Long currentUserId);
    void deleteFolder(Long folderId, Long currentUserId);
    Folders editFolderTitle(Long folderId, String folderTitle);
    List<TaskCategories> detailsTaskCategories(Long id);
    void addCategoryToFolder(Long folderId, Long categoryId);
    void deleteCategoryFromFolder(Long folderId, Long categoryId);
}
