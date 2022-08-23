package bitlab.springbootfirstfinal.services;

import bitlab.springbootfirstfinal.models.Comments;
import bitlab.springbootfirstfinal.models.User;

import java.util.List;

public interface CommentsService {
    List<Comments> commentsByTaskId(Long taskId);
    void addComment(Comments comment, Long taskId, User currentUser);
}
