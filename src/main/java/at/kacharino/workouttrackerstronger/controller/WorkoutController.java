package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutDto;
import at.kacharino.workouttrackerstronger.security.AuthUtil;
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
    private AuthUtil authUtil;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutDto>> createWorkout(@RequestBody CreateWorkoutDto createWorkoutDto) {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var resultWorkoutDto = workoutService.createWorkout(authenticatedUserId, createWorkoutDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(resultWorkoutDto));
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDto>> getWorkoutById(@PathVariable Long workoutId) {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var resultWorkoutDto = workoutService.getWorkoutById(authenticatedUserId, workoutId);
        return ResponseEntity.ok(ApiResponse.success(resultWorkoutDto));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<WorkoutDto>>> getWorkoutsByUserId() {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var workouts = workoutService.getWorkoutsByUserId(authenticatedUserId);
        return ResponseEntity.ok(ApiResponse.success(workouts));
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity<ApiResponse<WorkoutDto>> updateWorkoutById(@PathVariable Long workoutId, @RequestBody UpdateWorkoutDto updateWorkoutDto) {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var workout = workoutService.updateWorkoutById(authenticatedUserId, workoutId, updateWorkoutDto);
        return ResponseEntity.ok(ApiResponse.success(workout));
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<ApiResponse<String>> deleteWorkoutById(@PathVariable Long workoutId) {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        String message = workoutService.deleteWorkoutById(authenticatedUserId, workoutId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success(message));

    }

}
