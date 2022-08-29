package bitlab.springbootfirstfinal.api;

import bitlab.springbootfirstfinal.dto.*;
import bitlab.springbootfirstfinal.mapper.CommentsMapper;
import bitlab.springbootfirstfinal.mapper.FoldersMapper;
import bitlab.springbootfirstfinal.mapper.TasksMapper;
import bitlab.springbootfirstfinal.mapper.UserMapper;
import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/categories")
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
    public ResponseEntity<CommentsDTO> addComment(@RequestBody CommentsDTO comment,
                                                  @PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(commentsService.addComment(comment, taskId, userMapper.toDto(userService.getCurrentuser())), HttpStatus.CREATED);
    }
}
