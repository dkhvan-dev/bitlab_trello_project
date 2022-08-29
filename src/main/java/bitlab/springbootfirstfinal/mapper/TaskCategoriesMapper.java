package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.TaskCategoriesDTO;
import bitlab.springbootfirstfinal.models.TaskCategories;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskCategoriesMapper {
    TaskCategoriesDTO toDto(TaskCategories taskCategories);
    TaskCategories toEntity(TaskCategoriesDTO taskCategoriesDTO);
    List<TaskCategoriesDTO> toDtoList(List<TaskCategories> taskCategoriesList);
    List<TaskCategories> toEntityList(List<TaskCategoriesDTO> taskCategoriesDTOList);
}
