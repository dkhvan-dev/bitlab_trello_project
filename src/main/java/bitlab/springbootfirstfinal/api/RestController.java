package bitlab.springbootfirstfinal.api;

import bitlab.springbootfirstfinal.dto.CommentsDTO;
import bitlab.springbootfirstfinal.dto.FoldersDTO;
import bitlab.springbootfirstfinal.dto.TasksDTO;
import bitlab.springbootfirstfinal.mapper.CommentsMapper;
import bitlab.springbootfirstfinal.mapper.FoldersMapper;
import bitlab.springbootfirstfinal.mapper.TasksMapper;
import bitlab.springbootfirstfinal.models.*;
import bitlab.springbootfirstfinal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class RestController {

    @Autowired
    private FoldersService foldersService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private TaskCategoriesService taskCategoriesService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private FoldersMapper foldersMapper;

    @Autowired
    private TasksMapper tasksMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    // FOLDERS
    @GetMapping("/folders/{currentUserId}")
    public ResponseEntity<List<FoldersDTO>> getAllFolders(@PathVariable(name = "currentUserId") Long currentUserId) {
        return new ResponseEntity<>(foldersMapper.toDtoList(foldersService.allFolders(currentUserId)), HttpStatus.OK);
    }

    @GetMapping(value = "folders/{currentUserId}/{folderId}")
    public ResponseEntity<FoldersDTO> getOneFolder(@PathVariable(name = "currentUserId") Long currentUserId,
                                                @PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(foldersMapper.toDto(foldersService.detailsFolder(folderId, currentUserId)), HttpStatus.OK);
    }

    @GetMapping("/taskCategories/{folderId}")
    public ResponseEntity<List<TaskCategories>> getCategoriesByFolder(@PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(foldersService.detailsTaskCategories(folderId), HttpStatus.OK);
    }

    // CATEGORIES
    @GetMapping("/categories")
    public ResponseEntity<List<TaskCategories>> getAllCategories() {
        return new ResponseEntity<>(taskCategoriesService.allCategories(), HttpStatus.OK);
    }

    // TASKS
    @GetMapping("/tasks/{folderId}")
    public ResponseEntity<List<TasksDTO>> getAllTasks(@PathVariable(name = "folderId") Long folderId) {
        return new ResponseEntity<>(tasksMapper.toDtoList(taskService.tasksList(folderId)), HttpStatus.OK);
    }

    @GetMapping("/tasks/{folderId}/{taskId}")
    public ResponseEntity<TasksDTO> getOneTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(tasksMapper.toDto(taskService.task(taskId)), HttpStatus.OK);
    }

    // TASK STATUS
    @GetMapping("/taskStatus")
    public ResponseEntity<List<TaskStatus>> getAllTaskStatus() {
        return new ResponseEntity<>(taskStatusService.allTaskStatus(), HttpStatus.OK);
    }

    // COMMENTS
    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentsDTO>> getCommentsByTask(@PathVariable(name = "taskId") Long taskId) {
        return new ResponseEntity<>(commentsMapper.toDtoList(commentsService.commentsByTaskId(taskId)), HttpStatus.OK);
    }
}
