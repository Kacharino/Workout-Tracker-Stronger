package at.kacharino.workouttrackerstronger.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
