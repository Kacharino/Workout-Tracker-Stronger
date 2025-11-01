package at.kacharino.workouttrackerstronger.mappers;

import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
    User toEntity(RegisterRequestDto registerRequestDto);
}