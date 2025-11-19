package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.ApiResponse;
import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.services.WorkoutService;
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
     */

    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutDto>> createWorkout(@RequestBody WorkoutDto workoutDto) {
        var resultWorkoutDto = workoutService.createWorkout(workoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(resultWorkoutDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutDto>> getWorkoutById(@PathVariable Long id) {
        var resultWorkoutDto = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(ApiResponse.success(resultWorkoutDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<WorkoutDto>>> getWorkoutsByUserId(@PathVariable Long userId) {
        var workouts = workoutService.getWorkoutsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(workouts));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutDto>> updateWorkoutById(@PathVariable Long id, @RequestBody UpdateWorkoutDto updateWorkoutDto) {
        var workout = workoutService.updateWorkoutById(id, updateWorkoutDto);
        return ResponseEntity.ok(ApiResponse.success(workout));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWorkoutById(@PathVariable Long id) {
        String message = workoutService.deleteWorkoutById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(message));

    }

}
