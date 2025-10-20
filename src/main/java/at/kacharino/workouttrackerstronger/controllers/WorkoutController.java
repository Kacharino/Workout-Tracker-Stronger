package at.kacharino.workouttrackerstronger.controllers;

import at.kacharino.workouttrackerstronger.dtos.ApiResponse;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.services.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/workouts")
public class WorkoutController {
    private WorkoutService workoutService;
    /**
     * Todo:
     * deleteWorkout, getWorkoutFromUserId, getWorkoutbyId, updateWorkout
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



}
