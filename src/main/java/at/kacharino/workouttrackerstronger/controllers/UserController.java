package at.kacharino.workouttrackerstronger.controllers;

import at.kacharino.workouttrackerstronger.dtos.*;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import at.kacharino.workouttrackerstronger.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;
    UserService userService;

    /*TODO:
     * Nach all die Core Features: Authentifizierung mit JWT
     * */

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.registerNewUser(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        userService.loginUser(loginRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User login successful"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userDto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("User deleted successfully"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUserById(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        var userDto = userService.updateUserById(id, updateUserDto);
        return ResponseEntity.ok(ApiResponse.success(userDto));

    }

}
