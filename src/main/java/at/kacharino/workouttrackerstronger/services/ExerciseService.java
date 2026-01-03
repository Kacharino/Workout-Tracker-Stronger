package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.entities.Exercise;
import at.kacharino.workouttrackerstronger.repositories.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }
}
