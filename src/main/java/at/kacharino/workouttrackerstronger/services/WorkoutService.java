package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.entities.Workout;
import at.kacharino.workouttrackerstronger.exceptions.NoContentException;
import at.kacharino.workouttrackerstronger.mappers.WorkoutMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService {
    private WorkoutRepository workoutRepository;
    private UserRepository userRepository;
    private WorkoutMapper workoutMapper;

    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        //userId muss existieren
        if (userRepository.findById(workoutDto.getUserId()).isEmpty()) {
            throw new IllegalArgumentException("User does not exists.");
        } else {
            Workout workout = workoutMapper.toEntity(workoutDto);
            var savedWorkout = workoutRepository.save(workout);
            return workoutMapper.toDto(savedWorkout);
        }
    }

    public WorkoutDto getWorkoutById(Long id) {
        var workout = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout with given ID does not exist."));
        return workoutMapper.toDto(workout);
    }


    public List<WorkoutDto> getWorkoutByUserId(Long userId) {
        var workouts = workoutRepository.findByUserId(userId);
        if (workouts.isEmpty()) {
            throw new NoContentException("No workouts found for this user.");
        }
        return workouts.stream().map(workoutMapper::toDto).toList();
    }

    public WorkoutDto updateWorkoutById(Long id, UpdateWorkoutDto updateWorkoutDto) {
        var workout = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout with given ID does not exist."));

        // Optionale Updates pro feld
        if (updateWorkoutDto.getWorkoutName() != null) workout.setWorkoutName(updateWorkoutDto.getWorkoutName());
        if (updateWorkoutDto.getDate() != null) workout.setDate(updateWorkoutDto.getDate());
        if (updateWorkoutDto.getDuration() != null) workout.setDuration(updateWorkoutDto.getDuration());

        var savedWorkout = workoutRepository.save(workout);
        return workoutMapper.toDto(savedWorkout);
    }


    public String deleteWorkoutById(Long id) {
        var workout = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout with given ID does not exist."));
        workoutRepository.delete(workout);
        return "Workout deleted successfully.";
    }
}
