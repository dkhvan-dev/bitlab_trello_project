package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.models.Comments;
import bitlab.springbootfirstfinal.models.Tasks;
import bitlab.springbootfirstfinal.models.User;
import bitlab.springbootfirstfinal.repository.CommentsRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    TasksRepository tasksRepository;

    @Override
    public List<Comments> commentsByTaskId(Long taskId) {
        return commentsRepository.searchAllByTask_TaskIdOrderByCommentsIdDesc(taskId);
    }

    @Override
    public void addComment(Comments comment, Long taskId, User currentUser) {
        Tasks task = tasksRepository.findById(taskId).orElse(null);
        if (comment != null) {
            comment.setTask(task);
            comment.setCommentAuthor(currentUser.getFullName());
            commentsRepository.save(comment);
        }
    }
}
