package bitlab.springbootfirstfinal.services.impl;

import bitlab.springbootfirstfinal.dto.CommentsDTO;
import bitlab.springbootfirstfinal.dto.UserDTO;
import bitlab.springbootfirstfinal.mapper.CommentsMapper;
import bitlab.springbootfirstfinal.models.Comments;
import bitlab.springbootfirstfinal.models.Tasks;
import bitlab.springbootfirstfinal.models.User;
import bitlab.springbootfirstfinal.repository.CommentsRepository;
import bitlab.springbootfirstfinal.repository.TasksRepository;
import bitlab.springbootfirstfinal.services.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;
    private final TasksRepository tasksRepository;
    private final CommentsMapper commentsMapper;

    @Override
    public List<CommentsDTO> commentsByTaskId(Long taskId) {
        return commentsMapper.toDtoList(commentsRepository.searchAllByTask_TaskIdOrderByCommentsIdDesc(taskId));
    }

    @Override
    public CommentsDTO addComment(CommentsDTO commentDTO, Long taskId, UserDTO currentUser) {
        Comments comment = commentsMapper.toEntity(commentDTO);
        Tasks task = tasksRepository.findById(taskId).orElse(null);
        if (comment != null) {
            comment.setTask(task);
            comment.setCommentAuthor(currentUser.getFullName());
            return commentsMapper.toDto(commentsRepository.save(comment));
        }
        return null;
    }
}
