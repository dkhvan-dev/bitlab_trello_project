package bitlab.springbootfirstfinal.controllers;

import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.repository.CommentsRepository;
import bitlab.springbootfirstfinal.repository.FoldersRepository;
import bitlab.springbootfirstfinal.repository.TaskCategoriesRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    FoldersService foldersService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskCategoriesService taskCategoriesService;

    @Autowired
    CommentsService commentsService;

    @Autowired
    TaskStatusService taskStatusService;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<Folders> folders = foldersService.allFolders();
        model.addAttribute("folders", folders);
        return "index";
    }

    @GetMapping(value = "/detailsFolder/{folderId}")
    public String detailsFolder(@PathVariable(name = "folderId") Long folderId,
            Model model) {
        Folders folder = foldersService.detailsFolder(folderId);
        model.addAttribute("folder", folder);
        List<Tasks> tasks = taskService.tasksList(folderId);
        model.addAttribute("tasks", tasks);
        List<TaskCategories> taskCategories = taskCategoriesService.detailsTaskCategories(folderId);
        model.addAttribute("taskCategories", taskCategories);
        List<TaskCategories> allCategories = taskCategoriesService.allCategories();
        allCategories.removeAll(taskCategories);
        model.addAttribute("allCategories", allCategories);
        List<TaskStatus> taskStatusList = taskStatusService.allTaskStatus();
        model.addAttribute("taskStatusList", taskStatusList);
        return "detailsFolder";
    }

    @PostMapping(value = "/addFolder")
    public String addFolder(Folders folder) {
        Folders newFolder = foldersService.addFolder(folder);
        return (folder != null ? "redirect:/detailsFolder/" + newFolder.getFolderId() : "redirect:/");
    }

    @GetMapping(value = "/detailsTask/{folderId}/{taskId}")
    public String detailsTask(@PathVariable(name = "folderId") Long folderId,
            @PathVariable(name = "taskId") Long taskId,
            Model model) {
        List<Tasks> allTasks = taskService.tasksList(folderId);
        model.addAttribute("allTasks", allTasks);
        Tasks task = taskService.task(taskId);
        model.addAttribute("task", task);
        Folders folder = foldersService.detailsFolder(folderId);
        model.addAttribute("folder", folder);
        List<Comments> commentsByTaskId = commentsService.commentsByTaskId(taskId);
        model.addAttribute("commentsByTaskId", commentsByTaskId);
        List<TaskStatus> allTaskStatus = taskStatusService.allTaskStatus();
        model.addAttribute("allTaskStatus", allTaskStatus);

        boolean canUpdateTask = true;
        if (task.getTaskStatus().getTaskStatusId() > 2) {
            canUpdateTask = false;
        } else {
            canUpdateTask = true;
        }
        model.addAttribute("canUpdateTask", canUpdateTask);
        return "detailsTask";
    }

    @PostMapping(value = "/addTask")
    public String addTask(Tasks task,
                          @RequestParam(name = "folderId") Long folderId,
                          @RequestParam(name = "taskStatusId") TaskStatus taskStatusId,
                          Model model) {
        taskService.addTask(task, folderId, taskStatusId);
        return "redirect:/detailsFolder/" + folderId;
    }

    @PostMapping(value = "/updateTask")
    public String detailsTask(Tasks task,
                              @RequestParam(name = "folderId") Long folderId,
                              Model model) {
        if (taskService.updateTask(task, folderId) != null) {
            return "redirect:/detailsFolder/" + folderId;
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "taskId") Long taskId,
                             @RequestParam(name = "folderId") Long folderId) {
        taskService.deleteTask(taskId);
        return "redirect:/detailsFolder/" + folderId;
    }

    @PostMapping(value = "/addCategory")
    private String addCategory(@RequestParam(name = "folderId") Long folderId,
                               @RequestParam(name = "categoriesId") Long categoryId) {
        taskCategoriesService.addCategory(folderId, categoryId);
        return "redirect:/detailsFolder/" + folderId;
    }

    @PostMapping(value = "/deleteCategory")
    private String deleteCategory(@RequestParam(name = "folderId") Long folderId,
                                  @RequestParam(name = "categoriesId") Long categoriesId) {
        taskCategoriesService.deleteCategory(folderId, categoriesId);
        return "redirect:/detailsFolder/" + folderId;
    }

    @PostMapping(value = "/deleteFolder")
    public String deleteFolder(@RequestParam(name = "folderId") Long folderId) {
        foldersService.deleteFolder(folderId);
        return "redirect:/";
    }

    @PostMapping(value = "/editFolderTitle")
    public String editFolderTitle(@RequestParam(name = "folderId") Long folderId,
                                  @RequestParam(name = "folderTitle") String folderTitle,
                                  Model model) {
        Folders folder = foldersService.editFolderTitle(folderId, folderTitle);
        model.addAttribute("folder", folder);
        return "redirect:/detailsFolder/" + folder.getFolderId();
    }

    @PostMapping(value = "/addComment")
    public String addComment(Comments comment,
                             @RequestParam(name = "folderId") Long folderId,
                             @RequestParam(name = "taskId") Long taskId,
                             Model model) {
        commentsService.addComment(comment, taskId);
        return "redirect:/detailsTask/" + folderId + "/" + taskId;
    }

    @GetMapping(value = "/allCategories")
    public String allCategories(Model model) {
        List<TaskCategories> allCategories = taskCategoriesService.allCategories();
        model.addAttribute("allCategories", allCategories);
        return "categories";
    }

    @PostMapping(value = "/addToListCategory")
    public String addToListCategory(TaskCategories taskCategory) {
        taskCategoriesService.addToListCategory(taskCategory);
        return "redirect:/allCategories";
    }

    @PostMapping(value = "/deleteFromListCategory")
    public String deleteFromListCategory(@RequestParam(name = "categoriesId") Long categoriesId) {
        taskCategoriesService.deleteFromListCategory(categoriesId);
        return "redirect:/allCategories";
    }

    @PostMapping(value = "/editCategory")
    public String editCategory(TaskCategories taskCategory) {
        taskCategoriesService.editCategory(taskCategory);
        return "redirect:/allCategories";
    }
}
