package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutEntryDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.exceptions.AccessDeniedException;
import at.kacharino.workouttrackerstronger.exceptions.UserNotFoundException;
import at.kacharino.workouttrackerstronger.exceptions.ValidationException;
import at.kacharino.workouttrackerstronger.exceptions.WorkoutNotFoundException;
import at.kacharino.workouttrackerstronger.mappers.WorkoutMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService {
    private EntryService entryService;
    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;
    private WorkoutMapper workoutMapper;

    public WorkoutDto createWorkout(Long authenticatedUserId, CreateWorkoutDto createWorkoutDto) {
        if (createWorkoutDto.getWorkoutName() == null || createWorkoutDto.getWorkoutName().isBlank()) {
            throw new ValidationException("Workout name must not be empty.");
        }
        //userId muss existieren
        var user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + authenticatedUserId));

        var workout = workoutMapper.toEntity(createWorkoutDto);
        workout.setUser(user);

        var savedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(savedWorkout);
    }

    public WorkoutDto getWorkoutById(Long authenticatedUserId, Long workoutId) {
        var workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with given ID does not exist."));

        if (!authenticatedUserId.equals(workout.getUser().getId())) {
            throw new AccessDeniedException("Not allowed");
        }
        return workoutMapper.toDto(workout);
    }


    public List<WorkoutDto> getWorkoutsByUserId(Long authenticatedUserId) {
        var workouts = workoutRepository.findByUserId(authenticatedUserId);
        return workouts.stream().map(workoutMapper::toDto).toList();
    }

    public WorkoutDto updateWorkoutById(Long authenticatedUserId, Long id, UpdateWorkoutDto updateWorkoutDto) {
        var workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with given ID does not exist."));

        if (!workout.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("Not allowed");
        }
        // Optionale Updates pro feld
        if (updateWorkoutDto.getWorkoutName() != null) workout.setWorkoutName(updateWorkoutDto.getWorkoutName());
        if (updateWorkoutDto.getDate() != null) workout.setDate(updateWorkoutDto.getDate());
        if (updateWorkoutDto.getDuration() != null) workout.setDuration(updateWorkoutDto.getDuration());

        var savedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(savedWorkout);
    }

    public String deleteWorkoutById(Long authenticatedUserId, Long id) {
        var workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with given ID does not exist."));

        if (!workout.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("Not allowed");
        }

        workoutRepository.delete(workout);
        return "Workout deleted successfully.";
    }
}
