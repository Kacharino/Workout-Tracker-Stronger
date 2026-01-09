package at.kacharino.workouttrackerstronger.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BestWeightPerExerciseDto {
    private Long exerciseId;
    private String exerciseName;
    private BigDecimal bestWeight;
}
