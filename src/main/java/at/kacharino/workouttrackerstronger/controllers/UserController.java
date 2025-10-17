package at.kacharino.workouttrackerstronger.controllers;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        try {
            boolean success = userService.registerNewUser(registerRequestDto);
            if (success) return ResponseEntity.ok("User registered successfully");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error occurred during registration");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            boolean success = userService.loginUser(loginRequestDto);
            if (success) return ResponseEntity.ok("User login successful");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unknown error occurred during login");

        } catch (IllegalArgumentException e) {
            if (e.getMessage().toLowerCase().contains("password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleBadJsonForRegister(
            HttpServletRequest request) {

        if (request.getRequestURI().equals("/users/register")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("""
                            Your JSON body is empty or malformed.
                            Example of a valid register format:
                            {
                              "firstName": "Maxi",
                              "lastName": "Mausi",
                              "email": "mausi@example.com",
                              "password": "1234"
                            }
                            """);
        } else if (request.getRequestURI().equals("/users/login")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("""
                            Your JSON body is empty or malformed.
                            Example of a valid login format:
                            {
                              "email": "mausi@example.com",
                              "password": "1234"
                            }
                            """);
        }

        // alle anderen Endpoints: Standardantwort
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Request body is missing or malformed.");
    }


    /*TODO:
     * Nächster Schritt: Authentifizierung
     * */

}
