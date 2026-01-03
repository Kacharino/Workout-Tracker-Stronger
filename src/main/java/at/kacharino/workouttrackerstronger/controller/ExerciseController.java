package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.ApiResponse;
import at.kacharino.workouttrackerstronger.entities.Exercise;
import at.kacharino.workouttrackerstronger.services.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/exercises")
public class ExerciseController {
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Exercise>>> getListOfAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(ApiResponse.success(exercises));
    }

}
