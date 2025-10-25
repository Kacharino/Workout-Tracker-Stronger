package at.kacharino.workouttrackerstronger.controllers;

import at.kacharino.workouttrackerstronger.dtos.ApiResponse;
import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.exceptions.NoContentException;
import at.kacharino.workouttrackerstronger.services.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {
    private WorkoutService workoutService;

    /**
     * Todo:
     * deleteWorkout, updateWorkout
     */

    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutDto>> createWorkout(@RequestBody WorkoutDto workoutDto) {
        try {
            var resultWorkoutDto = workoutService.createWorkout(workoutDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(resultWorkoutDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutDto>> getWorkoutById(@PathVariable Long id) {
        try {
            var resultWorkoutDto = workoutService.getWorkoutById(id);
            return ResponseEntity.ok(ApiResponse.success(resultWorkoutDto));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WorkoutDto>>> getWorkoutByUserId(@PathVariable Long userId) {
        try {
            var workouts = workoutService.getWorkoutByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success(workouts));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutDto>> updateWorkoutById(@PathVariable Long id, @RequestBody UpdateWorkoutDto updateWorkoutDto) {
        try {
            var workout = workoutService.updateWorkoutById(id, updateWorkoutDto);
            return ResponseEntity.ok(ApiResponse.success(workout));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWorkoutById(@PathVariable Long id){
        try {
            String message = workoutService.deleteWorkoutById(id);
            return ResponseEntity.ok(ApiResponse.success(message));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

}
