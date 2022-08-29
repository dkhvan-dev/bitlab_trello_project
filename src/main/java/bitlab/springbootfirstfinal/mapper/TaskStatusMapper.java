package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.TaskStatusDTO;
import bitlab.springbootfirstfinal.models.TaskStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskStatusMapper {
    TaskStatusDTO toDto(TaskStatus taskStatus);
    TaskStatus toEntity(TaskStatusDTO taskStatusDTO);
    List<TaskStatusDTO> toDtoList(List<TaskStatus> taskStatusList);
    List<TaskStatus> toEntityList(List<TaskStatusDTO> taskStatusDTOS);
}
