package at.kacharino.workouttrackerstronger.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutEntryResponseDto {
    private Long entryId;
    private Long exerciseId;
    private String exerciseName;
    private String category;
    private List<SetEntryResponseDto> sets;
}
