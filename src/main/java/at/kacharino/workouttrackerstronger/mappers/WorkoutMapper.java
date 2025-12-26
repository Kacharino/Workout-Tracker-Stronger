package at.kacharino.workouttrackerstronger.mappers;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutDto;
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
    Workout toEntity(CreateWorkoutDto createWorkoutDto);


}