package bitlab.springbootfirstfinal.controllers;

import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.services.*;
import bitlab.springbootfirstfinal.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<Folders> folders = foldersService.allFolders();
        model.addAttribute("folders", folders);
        model.addAttribute("currentUser", getCurrentuser());
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profilePage(Model model) {
        model.addAttribute("currentUser", getCurrentuser());
        return "profile";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/auth")
    public String authPage(Model model) {
        model.addAttribute("currentUser", getCurrentuser());
        return "auth";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("currentUser", getCurrentuser());
        return "signUp";
    }

    @PostMapping(value = "/signUp")
    public String signUp(@RequestParam(name = "user_email") String userEmail,
            @RequestParam(name = "user_password") String userPassword,
            @RequestParam(name = "user_re_password") String userRePassword,
            @RequestParam(name = "user_full_name") String userFullName,
            Model model) {
        if (userPassword.equals(userRePassword)) {
            User newUser = new User();
            newUser.setEmail(userEmail);
            newUser.setPassword(userPassword);
            newUser.setFullName(userFullName);
            newUser = userService.newUser(newUser);
            if (newUser != null) {
                model.addAttribute("currentUser", getCurrentuser());
                return "redirect:/signUp?success";
            } else {
                return "redirect:/signUp?errorUnknown";
            }
        } else {
            return "redirect:/signUp?errorPassword";
        }
    }

    @PostMapping(value = "/updatePassword")
    public String updatePassword(@RequestParam(name = "user_old_password") String oldPassword,
                                 @RequestParam(name = "user_new_password") String newPassword,
                                 @RequestParam(name = "user_new_re_password") String newRePassword) {
        if (newPassword.equals(newRePassword)) {
            User userUpdatedPassword = userService.updatePassword(getCurrentuser(), oldPassword, newPassword);
            if (userUpdatedPassword != null){
                return "redirect:/profile?success";
            }
        }
        return "redirect:/profile?error";
    }

    @PostMapping(value = "/updateUserName")
    public String updateUserName(@RequestParam(name = "user_new_full_name") String fullName,
                                 Model model) {
        if (fullName != null) {
            User currentUser = userService.updateFullName(getCurrentuser(), fullName);
            updateUserData(fullName);
            model.addAttribute("currentUser", currentUser);
            return "redirect:/profile?success";
        }
        return "redirect:/profile?error";
    }

    @GetMapping(value = "/detailsFolder/{folderId}")
    public String detailsFolder(@PathVariable(name = "folderId") Long folderId,
            Model model) {
        Folders folder = foldersService.detailsFolder(folderId);
        model.addAttribute("folder", folder);
        List<Tasks> tasks = taskService.tasksList(folderId);
        model.addAttribute("tasks", tasks);
        List<TaskCategories> taskCategories = foldersService.detailsTaskCategories(folderId);
        model.addAttribute("taskCategories", taskCategories);
        List<TaskCategories> allCategories = taskCategoriesService.allCategories();
        allCategories.removeAll(taskCategories);
        model.addAttribute("allCategories", allCategories);
        List<TaskStatus> taskStatusList = taskStatusService.allTaskStatus();
        model.addAttribute("taskStatusList", taskStatusList);
        model.addAttribute("currentUser", getCurrentuser());
        return "detailsFolder";
    }

    @PostMapping(value = "/addFolder")
    public String addFolder(Folders folder) {
        Folders newFolder = new Folders();
        if (folder != null) {
            newFolder = foldersService.addFolder(folder);
        }
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
        model.addAttribute("currentUser", getCurrentuser());
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
    public String addCategoryToFolder(@RequestParam(name = "folderId") Long folderId,
                               @RequestParam(name = "categoriesId") Long categoryId) {
        foldersService.addCategoryToFolder(folderId, categoryId);
        return "redirect:/detailsFolder/" + folderId;
    }

    @PostMapping(value = "/deleteCategory")
    public String deleteCategory(@RequestParam(name = "folderId") Long folderId,
                                  @RequestParam(name = "categoriesId") Long categoriesId) {
        foldersService.deleteCategoryFromFolder(folderId, categoriesId);
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
        System.out.println(folderId);
        System.out.println(folderTitle);
        Folders folder = foldersService.editFolderTitle(folderId, folderTitle);
        model.addAttribute("folder", folder);
        return "redirect:/detailsFolder/" + folder.getFolderId();
    }

    @PostMapping(value = "/addComment")
    public String addComment(Comments comment,
                             @RequestParam(name = "folderId") Long folderId,
                             @RequestParam(name = "taskId") Long taskId,
                             Model model) {
        commentsService.addComment(comment, taskId, getCurrentuser());
        return "redirect:/detailsTask/" + folderId + "/" + taskId;
    }

    @GetMapping(value = "/allCategories")
    public String allCategories(Model model) {
        List<TaskCategories> allCategories = taskCategoriesService.allCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("currentUser", getCurrentuser());
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

    public User getCurrentuser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            return user;
        }
        return null;
    }

    public void updateUserData(String fullName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            user.setFullName(fullName);
        }
    }

}
