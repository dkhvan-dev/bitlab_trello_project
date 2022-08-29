package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.TasksDTO;
import bitlab.springbootfirstfinal.models.Tasks;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TasksMapper {
    TasksDTO toDto(Tasks tasks);
    Tasks toEntity(TasksDTO tasksDTO);
    List<TasksDTO> toDtoList(List<Tasks> tasksList);
    List<Tasks> toEntityList(List<TasksDTO> tasksDTOList);
}
