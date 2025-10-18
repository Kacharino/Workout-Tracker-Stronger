package at.kacharino.workouttrackerstronger.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
    private Double weight;
    private Double height;
}
