package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Comments;
import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;
import bitlab.springbootfirstfinal.models.Tasks;
import bitlab.springbootfirstfinal.repository.CommentsRepository;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TaskCategoriesRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.FoldersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoldersServiceImpl implements FoldersService {

    @Autowired
    FoldersRepository foldersRepository;

    @Autowired
    TasksRepository tasksRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    TaskCategoriesRepository taskCategoriesRepository;

    @Override
    public List<Folders> allFolders(Long currentUserId) {
        return foldersRepository.findFoldersByUserIdOrderByFolderIdAsc(currentUserId);
    }

    @Override
    public Folders addFolder(Folders folder) {
        return foldersRepository.save(folder);
    }

    @Override
    public Folders detailsFolder(Long id, Long currentUserId) {
        Folders folder = foldersRepository.findById(id).orElse(null);
        if (folder != null && folder.getUser().getId().equals(currentUserId)) {
            return folder;
        }
        return null;
    }

    @Override
    public void deleteFolder(Long folderId) {
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        if (folder != null) {
            folder.setCategories(new ArrayList<>());
            List<Tasks> tasks = tasksRepository.searchAllByFolder_FolderIdOrderByTaskId(folderId);
            for (int i = 0; i < tasks.size(); i++) {
                List<Comments> comments = commentsRepository.searchAllByTask_TaskIdOrderByCommentsIdDesc(tasks.get(i).getTaskId());
                commentsRepository.deleteAll(comments);
            }
            tasksRepository.deleteAll(tasks);
            foldersRepository.save(folder);
            foldersRepository.deleteById(folderId);
        }
    }

    @Override
    public Folders editFolderTitle(Long folderId, String folderTitle) {
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        if (folder != null && !folderTitle.isEmpty()) {
            folder.setFolderName(folderTitle);
            foldersRepository.save(folder);
            return folder;
        }
        return null;
    }

    @Override
    public List<TaskCategories> detailsTaskCategories(Long id) {
        Folders folder = foldersRepository.findById(id).orElse(null);
        if (folder != null) {
            return folder.getCategories();
        }
        return null;
    }

    @Override
    public void addCategoryToFolder(Long folderId, Long categoryId) {
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
    public void deleteCategoryFromFolder(Long folderId, Long categoryId) {
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
}
