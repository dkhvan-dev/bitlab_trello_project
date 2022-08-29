package bitlab.springbootfirstfinal.mapper;

import bitlab.springbootfirstfinal.dto.UserDTO;
import bitlab.springbootfirstfinal.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toDtoList(List<User> userList);
    List<User> toEntityList(List<UserDTO> userDTOList);
}
