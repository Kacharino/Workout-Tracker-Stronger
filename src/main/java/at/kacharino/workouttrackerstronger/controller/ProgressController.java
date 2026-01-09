package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.ApiResponse;
import at.kacharino.workouttrackerstronger.dtos.BestWeightPerExerciseDto;
import at.kacharino.workouttrackerstronger.security.AuthUtil;
import at.kacharino.workouttrackerstronger.services.ProgressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/progress")
public class ProgressController {
    private AuthUtil authUtil;
    private ProgressService progressService;

    @GetMapping("/best-weight-per-exercise")
    public ResponseEntity<ApiResponse<List<BestWeightPerExerciseDto>>> getBestWeightPerExercise() {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var bestWeightsList = progressService.getBestWeightPerExercise(authenticatedUserId);
        return ResponseEntity.ok(ApiResponse.success(bestWeightsList));
    }

}
