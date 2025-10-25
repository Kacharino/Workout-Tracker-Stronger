package at.kacharino.workouttrackerstronger.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkoutDto {
    private String workoutName;
    private LocalDate date;
    private LocalTime duration;
}