package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.FoldersDTO;
import bitlab.springbootfirstfinal.models.Folders;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoldersMapper {
    FoldersDTO toDto(Folders folders);
    Folders toEntity(FoldersDTO foldersDTO);
    List<FoldersDTO> toDtoList(List<Folders> foldersList);
    List<Folders> toEntityList(List<FoldersDTO> foldersDTOList);
}
