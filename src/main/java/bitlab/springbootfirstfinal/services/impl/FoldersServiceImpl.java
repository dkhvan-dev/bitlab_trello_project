package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.dto.FoldersDTO;
import bitlab.springbootfirstfinal.dto.TaskCategoriesDTO;
import bitlab.springbootfirstfinal.mapper.FoldersMapper;
import bitlab.springbootfirstfinal.mapper.TaskCategoriesMapper;
import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.repository.*;
import bitlab.springbootfirstfinal.services.FoldersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoldersServiceImpl implements FoldersService {

    private final FoldersRepository foldersRepository;
    private final TasksRepository tasksRepository;
    private final CommentsRepository commentsRepository;
    private final TaskCategoriesRepository taskCategoriesRepository;
    private final UserRepository userRepository;
    private final FoldersMapper foldersMapper;
    private final TaskCategoriesMapper taskCategoriesMapper;

    @Override
    public List<FoldersDTO> allFolders(Long currentUserId) {
        return foldersMapper.toDtoList(foldersRepository.findFoldersByUserIdOrderByFolderIdAsc(currentUserId));
    }

    @Override
    public Folders addFolder(Folders folder, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId).orElse(null);
        if (currentUser != null) {
            folder.setUser(currentUser);
        }
        return foldersRepository.save(folder);
    }

    @Override
    public FoldersDTO detailsFolder(Long id, Long currentUserId) {
        Folders folder = foldersRepository.findById(id).orElse(null);
        if (folder != null && folder.getUser().getId().equals(currentUserId)) {
            return foldersMapper.toDto(folder);
        }
        return null;
    }

    @Override
    public void deleteFolder(Long folderId, Long currentUserId) {
        System.out.println(folderId);
        Folders folder = foldersRepository.findById(folderId).orElse(null);
        if (folder != null && folder.getUser().getId().equals(currentUserId)) {
            folder.setCategories(new ArrayList<>());
            List<Tasks> tasks = tasksRepository.searchAllByFolder_FolderIdOrderByTaskId(folderId);
            for (int i = 0; i < tasks.size(); i++) {
                List<Comments> comments = commentsRepository.searchAllByTask_TaskIdOrderByCommentsIdDesc(tasks.get(i).getTaskId());
                commentsRepository.deleteAll(comments);
            }
            tasksRepository.deleteAll(tasks);
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
    public List<TaskCategoriesDTO> detailsTaskCategories(Long id) {
        Folders folder = foldersRepository.findById(id).orElse(null);
        if (folder != null) {
            return taskCategoriesMapper.toDtoList(folder.getCategories());
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
