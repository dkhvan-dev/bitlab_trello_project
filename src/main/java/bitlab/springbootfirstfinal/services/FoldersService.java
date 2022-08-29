package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.dto.FoldersDTO;
import bitlab.springbootfirstfinal.dto.TaskCategoriesDTO;
import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;

import java.util.List;

public interface FoldersService {
    List<FoldersDTO> allFolders(Long currentUserId);
    Folders addFolder(Folders folder, Long currentUserId);
    FoldersDTO detailsFolder(Long id, Long currentUserId);
    void deleteFolder(Long folderId, Long currentUserId);
    Folders editFolderTitle(Long folderId, String folderTitle);
    List<TaskCategoriesDTO> detailsTaskCategories(Long id);
    void addCategoryToFolder(Long folderId, Long categoryId);
    void deleteCategoryFromFolder(Long folderId, Long categoryId);
}
