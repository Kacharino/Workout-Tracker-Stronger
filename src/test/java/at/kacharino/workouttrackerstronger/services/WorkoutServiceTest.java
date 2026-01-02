package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.entities.Workout;
import at.kacharino.workouttrackerstronger.exceptions.NoContentException;
import at.kacharino.workouttrackerstronger.exceptions.ValidationException;
import at.kacharino.workouttrackerstronger.exceptions.WorkoutNotFoundException;
import at.kacharino.workouttrackerstronger.mappers.WorkoutMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {
    @InjectMocks
    WorkoutService workoutService;

    @Mock
    UserRepository userRepository;

    @Mock
    WorkoutRepository workoutRepository;

    @Mock
    WorkoutMapper workoutMapper;

    // --------------------------------------------------------
    // createWorkout()
    // --------------------------------------------------------

    @Test
    void shouldCreateWorkout_whenWorkoutDtoIsValid() {
        Long authenticatedUserId = 1L;

        var createWorkoutDto = new CreateWorkoutDto();
        createWorkoutDto.setWorkoutName("Push");
        createWorkoutDto.setDate(LocalDate.of(2025, 11, 12));
        createWorkoutDto.setDuration(LocalTime.of(1,2, 22));

        var user = new User();
        var workout = new Workout();
        var savedWorkout = new Workout();
        var workoutDto = new WorkoutDto();

        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(user));
        when(workoutMapper.toEntity(createWorkoutDto)).thenReturn(workout);
        when(workoutRepository.save(workout)).thenReturn(savedWorkout);
        when(workoutMapper.toDto(savedWorkout)).thenReturn(workoutDto);

        var result = workoutService.createWorkout(authenticatedUserId, createWorkoutDto);

        verify(userRepository).findById(authenticatedUserId);
        verify(workoutMapper).toEntity(createWorkoutDto);
        verify(workoutRepository).save(workout);
        verify(workoutMapper).toDto(savedWorkout);

        assertEquals(workoutDto, result);
    }

    @Test
    void shouldThrowValidationException_whenWorkoutDtoNameIsEmpty() {
        var createWorkoutDto = new CreateWorkoutDto();
        Long authenticatedUserId = 1L;

        assertThrows(ValidationException.class,
                () -> workoutService.createWorkout(authenticatedUserId, createWorkoutDto));
    }

    @Test
    void shouldThrowValidationException_whenWorkoutDtoNameIsBlank() {
        var createWorkoutDto = new CreateWorkoutDto();
        Long authenticatedUserId = 1L;
        createWorkoutDto.setWorkoutName("   ");

        assertThrows(ValidationException.class,
                () -> workoutService.createWorkout(authenticatedUserId, createWorkoutDto));
    }

    // --------------------------------------------------------
    // getWorkoutById()
    // --------------------------------------------------------

    @Test
    void shouldReturnWorkoutDto_whenWorkoutIdWasFound() {
        Long workoutId = 2L;
        Long authId = 1L;

        var workout = new Workout();
        var workoutDto = new WorkoutDto();
        workoutDto.setId(workoutId);
        var user = new User();
        user.setId(authId);
        workout.setUser(user);

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(workoutMapper.toDto(workout)).thenReturn(workoutDto);

        var result = workoutService.getWorkoutById(authId, workoutId);

        verify(workoutRepository).findById(workoutId);
        verify(workoutMapper).toDto(workout);

        assertEquals(workoutDto, result);
    }

    @Test
    void shouldThrowWorkoutNotFoundException_whenWorkoutIdWasNotFound() {
        Long id = 1L;
        Long authId = 1L;

        when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class,
                () -> workoutService.getWorkoutById(authId, id));
    }

    // --------------------------------------------------------
    // getWorkoutsByUserId()
    // --------------------------------------------------------

    @Test
    void shouldReturnListOfWorkouts_whenWorkoutsFoundByUserId() {
        Long id = 1L;

        var workout1 = new Workout();
        var workout2 = new Workout();
        var workouts = List.of(workout1, workout2);
        var workoutDto1 = new WorkoutDto();
        var workoutDto2 = new WorkoutDto();

        when(workoutRepository.findByUserId(id)).thenReturn(workouts);
        when(workoutMapper.toDto(workout1)).thenReturn(workoutDto1);
        when(workoutMapper.toDto(workout2)).thenReturn(workoutDto2);

        var workoutDtos = List.of(workoutDto1, workoutDto2);

        var result = workoutService.getWorkoutsByUserId(id);

        verify(workoutRepository).findByUserId(id);
        verify(workoutMapper).toDto(workout1);
        verify(workoutMapper).toDto(workout2);

        assertEquals(workoutDtos, result);

    }

    @Test
    void shouldThrowNoContentException_whenNoWorkoutsAreFound() {
        Long id = 1L;

        when(workoutRepository.findByUserId(id)).thenReturn(List.of());

        assertThrows(NoContentException.class,
                () -> workoutService.getWorkoutsByUserId(id));
    }

    // --------------------------------------------------------
    // updateWorkoutById()
    // --------------------------------------------------------

    @Test
    void shouldReturnWorkoutDto_whenUpdatedWorkoutDtoIsValid() {
        Long workoutId = 1L;
        Long authenticatedUserId = 1L;
        var user = new User();
        user.setId(authenticatedUserId);
        var updateWorkoutDto = new UpdateWorkoutDto();
        var workout = new Workout();
        workout.setUser(user);
        var savedWorkout = new Workout();
        var workoutDto = new WorkoutDto();

        updateWorkoutDto.setWorkoutName("Pull");
        updateWorkoutDto.setDuration(LocalTime.of(1, 10, 22));
        updateWorkoutDto.setDate(LocalDate.of(2025, 11, 13));

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(workoutRepository.save(workout)).thenReturn(savedWorkout);
        when(workoutMapper.toDto(savedWorkout)).thenReturn(workoutDto);

        var result = workoutService.updateWorkoutById(authenticatedUserId, workoutId, updateWorkoutDto);

        verify(workoutRepository).findById(workoutId);
        verify(workoutRepository).save(workout);
        verify(workoutMapper).toDto(savedWorkout);
        assertEquals(workoutDto, result);
    }

    @Test
    void shouldReturnWorkoutDto_whenUpdatedWorkoutDtoFieldsIsNull() {
        Long id = 1L;
        Long authenticatedUserId = 1L;

        var user = new User();
        user.setId(authenticatedUserId);
        var updateWorkoutDto = new UpdateWorkoutDto();
        var workout = new Workout();
        workout.setUser(user);
        var savedWorkout = new Workout();
        var workoutDto = new WorkoutDto();

        when(workoutRepository.findById(id)).thenReturn(Optional.of(workout));
        when(workoutRepository.save(workout)).thenReturn(savedWorkout);
        when(workoutMapper.toDto(savedWorkout)).thenReturn(workoutDto);

        var result = workoutService.updateWorkoutById(authenticatedUserId, id, updateWorkoutDto);

        verify(workoutRepository).findById(id);
        verify(workoutRepository).save(workout);
        verify(workoutMapper).toDto(savedWorkout);
        assertEquals(workoutDto, result);
    }

    @Test
    void shouldThrowWorkoutNotFoundException_whenWorkoutWasNotFoundById() {
        Long id = 1L;
        Long authenticatedUserId = 1L;

        var updateWorkoutDto = new UpdateWorkoutDto();

        when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class,
                () -> workoutService.updateWorkoutById(authenticatedUserId, id, updateWorkoutDto));
    }

    // --------------------------------------------------------
    // deleteWorkoutById()
    // --------------------------------------------------------

    @Test
    void shouldReturnStringWithSuccessMessage_whenWorkoutToBeDeletedFoundWithId() {
        Long id = 1L;
        Long authenticatedUserId = 1L;

        var user = new User();
        user.setId(authenticatedUserId);
        var workout = new Workout();
        workout.setUser(user);

        when(workoutRepository.findById(id)).thenReturn(Optional.of(workout));

        var result = workoutService.deleteWorkoutById(authenticatedUserId, id);

        verify(workoutRepository).findById(id);
        verify(workoutRepository).delete(workout);

        assertEquals("Workout deleted successfully.", result);
    }

    @Test
    void shouldThrowWorkoutNotFoundException_whenWorkoutWasNotFoundByIdWhenTryingToDelete() {
        Long id = 1L;
        Long authenticatedUserId = 1L;


        when(workoutRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class,
                () -> workoutService.deleteWorkoutById(authenticatedUserId, id));
    }
}
