package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.entities.Workout;
import at.kacharino.workouttrackerstronger.mappers.WorkoutMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
