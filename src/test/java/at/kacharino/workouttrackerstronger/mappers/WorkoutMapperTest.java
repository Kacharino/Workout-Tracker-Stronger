package at.kacharino.workouttrackerstronger.mappers;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.entities.Workout;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutMapperTest {

    private final WorkoutMapper workoutMapper = Mappers.getMapper(WorkoutMapper.class);

    @Test
    void shouldMapWorkoutToDto() {
        // Arrange
        User user = new User();
        user.setId(10L);

        Workout workout = new Workout();
        workout.setId(1L);
        workout.setWorkoutName("Push");
        workout.setDate(LocalDate.of(2025, 1, 1));
        workout.setDuration(LocalTime.of(1, 30, 0));
        workout.setUser(user);

        // Act
        WorkoutDto dto = workoutMapper.toDto(workout);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Push", dto.getWorkoutName());
        assertEquals(LocalDate.of(2025, 1, 1), dto.getDate());
        assertEquals(LocalTime.of(1, 30, 0), dto.getDuration());
        assertEquals(10L, dto.getUserId());
    }

    @Test
    void shouldMapDtoToWorkout() {
        // Arrange
        Long authenticatedUserId = 1L;
        CreateWorkoutDto createWorkoutDto = new CreateWorkoutDto();
        createWorkoutDto.setWorkoutName("Leg Day");
        createWorkoutDto.setDate(LocalDate.of(2025, 2, 15));
        createWorkoutDto.setDuration(LocalTime.of(2, 0, 0));

        var user = new User();
        user.setId(1L);

        // Act
        var workout = workoutMapper.toEntity(createWorkoutDto);
        workout.setUser(user);

        // Assert
        assertNotNull(workout);
        assertEquals("Leg Day", workout.getWorkoutName());
        assertEquals(LocalDate.of(2025, 2, 15), workout.getDate());
        assertEquals(LocalTime.of(2, 0, 0), workout.getDuration());

        assertNotNull(workout.getUser());
        assertEquals(authenticatedUserId, workout.getUser().getId());
    }
}