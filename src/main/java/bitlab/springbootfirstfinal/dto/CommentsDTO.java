package bitlab.springbootfirstfinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsDTO {
    private Long commentsId;
    private String commentAuthor;
    private String comment;
    private TasksDTO task;
}
