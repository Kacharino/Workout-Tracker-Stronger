package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /* TODO:
     * resetPassword,
     * deleteUser, getUserById
     * */

    public boolean registerNewUser(RegisterRequestDto registerRequestDto) {
        // Check if registerRequestDto has an empty or null field
        if (registerRequestDto.getFirstName() == null || registerRequestDto.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("FirstName must not be null or empty.");
        }
        if (registerRequestDto.getLastName() == null || registerRequestDto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("LastName must not be null or empty.");
        }
        if (registerRequestDto.getEmail() == null || registerRequestDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty.");
        }
        if (registerRequestDto.getPassword() == null || registerRequestDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty.");
        }

        //Check if email already exists
        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A user with the given email already exists: " + registerRequestDto.getEmail());
        }

        // aus der Dto einen User machen
        User newUser = new User();
        newUser.setFirstName(registerRequestDto.getFirstName());
        newUser.setLastName(registerRequestDto.getLastName());
        newUser.setEmail(registerRequestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword())); // Password hashen und setzten

        userRepository.save(newUser);
        System.out.println("User registered: " + newUser.getId());
        return true;
    }

    public boolean loginUser(LoginRequestDto loginRequestDto) {
        var userFromDB = userRepository.findByEmail(loginRequestDto.getEmail());
        if (userFromDB.isEmpty()) {
            throw new IllegalArgumentException("User with the given email dont exists.");
        }

        if (!(passwordEncoder.matches(loginRequestDto.getPassword(), userFromDB.get().getPassword()))) {
            throw new IllegalArgumentException("Password is incorrect");
        }
        return true;
    }

    public UserDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }


    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


}
