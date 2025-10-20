package at.kacharino.workouttrackerstronger.mappers;

import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.entities.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    // Entity → DTO (User → userId)
    @Mapping(source = "user.id", target = "userId")
    WorkoutDto toDto(Workout workout);

    // DTO → Entity (userId → User)
    @Mapping(target = "user", expression = "java(mapUser(workoutDto.getUserId()))")
    Workout toEntity(WorkoutDto workoutDto);

    // Hilfsmethode für MapStruct (manuell erstellt)
    default User mapUser(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }
}