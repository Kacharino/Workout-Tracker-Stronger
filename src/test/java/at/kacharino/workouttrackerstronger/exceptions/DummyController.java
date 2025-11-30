package at.kacharino.workouttrackerstronger.exceptions;


/* -------------------------------------------------------------------------
   DUMMY CONTROLLER (nur für Tests)
   Jeder Endpoint wirft eine Exception → GlobalExceptionHandler fängt sie ab.
   ------------------------------------------------------------------------- */

import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@TestComponent
@RestController
public class DummyController {

    @GetMapping("/test-user-not-found")
    public void throwUserNotFound() {
        throw new UserNotFoundException("User not found");
    }

    @GetMapping("/test-user-already-exists")
    public void throwUserAlreadyExists() {
        throw new UserAlreadyExistsException("User already exists");
    }

    @GetMapping("/test-workout-not-found")
    public void throwWorkoutNotFound() {
        throw new WorkoutNotFoundException("Workout not found");
    }

    @GetMapping("/test-no-content")
    public void throwNoContent() {
        throw new NoContentException("No content available");
    }

    @GetMapping("/test-illegal-argument")
    public void throwIllegalArgument() {
        throw new IllegalArgumentException("Illegal argument");
    }

    @GetMapping("/test-validation")
    public void throwValidation() {
        throw new ValidationException("Validation failed");
    }

    @GetMapping("/test-invalid-credentials")
    public void throwInvalidCredentials() {
        throw new InvalidCredentialsException("Invalid credentials");
    }

    @GetMapping("/test-generic")
    public void throwGeneric() {
        throw new RuntimeException("something went wrong");
    }

    @PostMapping("/users/register")
    public void throwForRegister() {
        throw new HttpMessageNotReadableException("bad json");
    }

    @PostMapping("/users/login")
    public void throwForLogin() {
        throw new HttpMessageNotReadableException("bad json");
    }

    @PostMapping("/*")
    public void throwGenericForAnyOther() {
        throw new HttpMessageNotReadableException("Malformed JSON");
    }
}
