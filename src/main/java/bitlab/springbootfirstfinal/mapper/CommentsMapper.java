package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.CommentsDTO;
import bitlab.springbootfirstfinal.models.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentsDTO toDto(Comments comments);

    Comments toEntity(CommentsDTO commentsDTO);
    List<CommentsDTO> toDtoList(List<Comments> commentsList);
    List<Comments> toEntityList(List<CommentsDTO> commentsDTOList);
}
