package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.*;
import at.kacharino.workouttrackerstronger.security.AuthUtil;
import at.kacharino.workouttrackerstronger.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private AuthUtil authUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.registerNewUser(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.loginUser(loginRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(token));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getUserById() {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        UserDto userDto = userService.getUserById(authenticatedUserId);
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> deleteUserById() {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        userService.deleteUserById(authenticatedUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("User deleted successfully"));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> updateUserById(@RequestBody UpdateUserDto updateUserDto) {
        Long authenticatedUserId = authUtil.getAuthenticatedUserId();
        var userDto = userService.updateUserById(authenticatedUserId, updateUserDto);
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

}
