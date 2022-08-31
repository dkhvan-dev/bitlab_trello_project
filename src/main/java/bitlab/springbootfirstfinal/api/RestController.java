package bitlab.springbootfirstfinal.api;

import bitlab.springbootfirstfinal.dto.*;
import bitlab.springbootfirstfinal.mapper.CommentsMapper;
import bitlab.springbootfirstfinal.mapper.FoldersMapper;
import bitlab.springbootfirstfinal.mapper.TasksMapper;
import bitlab.springbootfirstfinal.mapper.UserMapper;
import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.services.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class RestController {

    private final FoldersService foldersService;
    private final TaskService taskService;
    private final CommentsService commentsService;
    private final TaskCategoriesService taskCategoriesService;
    private final TaskStatusService taskStatusService;
    private final UserService userService;
    private final FoldersMapper foldersMapper;
    private final TasksMapper tasksMapper;
    private final CommentsMapper commentsMapper;
    private final UserMapper userMapper;


    // FOLDERS
    @GetMapping("/folders/{currentUserId}")
    public ResponseEntity<List<FoldersDTO>> getAllFolders(@PathVariable(name = "currentUserId") Long currentUserId) {
        return new ResponseEntity<>(foldersService.allFolders(currentUserId), HttpStatus.OK);
    }

    @GetMapping(value = "folders/{currentUserId}/{folderId}")
    public ResponseEntity<FoldersDTO> getOneFolder(@PathVariable(name = "currentUserId") Long currentUserId,
                                                @PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(foldersService.detailsFolder(folderId, currentUserId), HttpStatus.OK);
    }

    @GetMapping("/taskCategories/{folderId}")
    public ResponseEntity<List<TaskCategoriesDTO>> getCategoriesByFolder(@PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(foldersService.detailsTaskCategories(folderId), HttpStatus.OK);
    }

    // CATEGORIES
    @GetMapping("/allCategories")
    public ResponseEntity<List<TaskCategoriesDTO>> getAllCategories() {
        return new ResponseEntity<>(taskCategoriesService.allCategories(), HttpStatus.OK);
    }

    // TASKS
    @GetMapping("/tasks/{folderId}")
    public ResponseEntity<List<TasksDTO>> getAllTasks(@PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(taskService.tasksList(folderId), HttpStatus.OK);
    }

    @GetMapping("/tasks/{folderId}/{taskId}")
    public ResponseEntity<TasksDTO> getOneTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(taskService.task(taskId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/tasks/{folderId}/{taskId}")
    public void deleteTask(@PathVariable(name = "folderId") Long folderId,
                           @PathVariable(name = "taskId") Long taskId) {
        taskService.deleteTask(taskId);
    }

    // TASK STATUS
    @GetMapping("/taskStatus")
    public ResponseEntity<List<TaskStatusDTO>> getAllTaskStatus() {
        return new ResponseEntity<>(taskStatusService.allTaskStatus(), HttpStatus.OK);
    }

    // COMMENTS
    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentsDTO>> getCommentsByTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(commentsService.commentsByTaskId(taskId), HttpStatus.OK);
    }

    @PostMapping(value = "/comments/{taskId}")
//    public ResponseEntity<CommentsDTO> addComment(@RequestBody CommentsDTO commentDTO,
//                                                  @PathVariable(name = "taskId") Long taskId) {
//        Comments comment = commentsMapper.toEntity(comment);
//        return new ResponseEntity<>(commentsService.addComment(commentDTO, taskId), HttpStatus.CREATED);
//    }
    public ResponseEntity<Comments> addComment(@RequestBody Comments comments,
                                  @PathVariable(name = "taskId") Long taskId) {

        return new ResponseEntity<>(commentsService.addComment(comments, taskId, getCurrentUser(comments.getTask().getFolder().getUser().getEmail())), HttpStatus.OK);
//        return new ResponseEntity<>(comment.getTask().getFolder().getUser().getEmail(), HttpStatus.OK);


//                commentsService.addComment(commentsDTO, taskId, getCurrentUser(comment.getTask().getFolder().getUser().getEmail()));
    }

    // USERS
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    public User getCurrentUser(String email) {
        for (int i = 0; i < userService.getAllUsers().size(); i++) {
            if (userService.getAllUsers().get(i).getEmail().equals(email)) {
                return userService.getAllUsers().get(i);
            }
        }
//        User userCurrent = userService.getAllUsers()
//                .stream()
//                .filter(user -> email.equals(user.getEmail()))
//                .findFirst()
//                .get();
//        System.out.println(userService.getAllUsers().get(0).getEmail());
//        return userMapper.toDto(userCurrent) ;
        return null;
    }
}
