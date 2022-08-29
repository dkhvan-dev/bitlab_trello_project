package bitlab.springbootfirstfinal.dto;

import bitlab.springbootfirstfinal.models.TaskCategories;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoldersDTO {
    private Long folderId;
    private String folderName;
    private List<TaskCategories> categories;
    private UserDTO user;
}
