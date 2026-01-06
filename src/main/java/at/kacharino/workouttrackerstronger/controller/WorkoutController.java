package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.*;
import at.kacharino.workouttrackerstronger.security.AuthUtil;
import at.kacharino.workouttrackerstronger.services.EntryService;
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
    private EntryService entryService;
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
        return ResponseEntity.ok().body(ApiResponse.success(message));

    }

    @PostMapping("/{workoutId}/entries")
    public ResponseEntity<ApiResponse<Long>> addWorkoutEntryInWorkout(@PathVariable Long workoutId, @RequestBody CreateWorkoutEntryDto createWorkoutEntryDto){
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        Long workoutEntryId = entryService.addEntry(authenticatedUserId, workoutId, createWorkoutEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(workoutEntryId));
    }

    @GetMapping("/{workoutId}/entries")
    public ResponseEntity<ApiResponse<List<WorkoutEntryResponseDto>>> getWorkoutEntries(@PathVariable Long workoutId){
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        List<WorkoutEntryResponseDto> workoutEntryResponseDtos = entryService.getWorkoutEntries(authenticatedUserId, workoutId);
        return ResponseEntity.ok(ApiResponse.success(workoutEntryResponseDtos));
    }


}
