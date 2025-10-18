package at.kacharino.workouttrackerstronger.controllers;

import at.kacharino.workouttrackerstronger.dtos.*;
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
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        try {
            boolean success = userService.registerNewUser(registerRequestDto);
            if (success) return ResponseEntity.ok(ApiResponse.success("User registered successfully"));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Unknown error occurred during registration"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            boolean success = userService.loginUser(loginRequestDto);
            if (success) return ResponseEntity.ok(ApiResponse.success("User login successful"));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Unknown error occurred during login"));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().toLowerCase().contains("password")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e.getMessage()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id){
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.success(userDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUserById(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        try {
            var userDto = userService.updateUserById(id, updateUserDto);
            return ResponseEntity.ok(ApiResponse.success(userDto));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().toLowerCase().contains("email")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(e.getMessage()));
            } else if (e.getMessage().toLowerCase().contains("not found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Internal Server Error."));
            }
        }
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleBadJsonForRegister(
            HttpServletRequest request) {

        if (request.getRequestURI().equals("/users/register")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("""
                            Your JSON body is empty or malformed.
                            Example of a valid register format:
                            {
                              "firstName": "Maxi",
                              "lastName": "Mausi",
                              "email": "mausi@example.com",
                              "password": "1234"
                            }
                            """));
        } else if (request.getRequestURI().equals("/users/login")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("""
                            Your JSON body is empty or malformed.
                            Example of a valid login format:
                            {
                              "email": "mausi@example.com",
                              "password": "1234"
                            }
                            """));
        }

        // alle anderen Endpoints: Standardantwort
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("Request body is missing or malformed."));
    }


    /*TODO:
     * Nächster Schritt: Authentifizierung
     * */

}
