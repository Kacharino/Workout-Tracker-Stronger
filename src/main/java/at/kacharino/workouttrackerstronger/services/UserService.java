package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateUserDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.exceptions.InvalidCredentialsException;
import at.kacharino.workouttrackerstronger.exceptions.UserAlreadyExistsException;
import at.kacharino.workouttrackerstronger.exceptions.UserNotFoundException;
import at.kacharino.workouttrackerstronger.exceptions.ValidationException;
import at.kacharino.workouttrackerstronger.mappers.UserMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    /* TODO:
     * resetPassword
     * */

    public void registerNewUser(RegisterRequestDto registerRequestDto) {
        // Check if registerRequestDto has an empty or null field
        if (registerRequestDto.getFirstName() == null || registerRequestDto.getFirstName().trim().isEmpty()) {
            throw new ValidationException("FirstName must not be null or empty.");
        }
        if (registerRequestDto.getLastName() == null || registerRequestDto.getLastName().trim().isEmpty()) {
            throw new ValidationException("LastName must not be null or empty.");
        }
        if (registerRequestDto.getEmail() == null || registerRequestDto.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email must not be null or empty.");
        }
        if (registerRequestDto.getPassword() == null || registerRequestDto.getPassword().trim().isEmpty()) {
            throw new ValidationException("Password must not be null or empty.");
        }

        //Check if email already exists
        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with the given email already exists: " + registerRequestDto.getEmail());
        }

        var newUser = userMapper.toEntity(registerRequestDto);
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));

        userRepository.save(newUser);
    }

    public void loginUser(LoginRequestDto loginRequestDto) {
        var user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with the given email dont exists."));

        if (!(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))) {
            throw new InvalidCredentialsException("Password is incorrect");
        }
    }

    public UserDto getUserById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }


    public UserDto updateUserById(Long id, UpdateUserDto updateUserDto) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // Optionale Updates pro feld
        if (updateUserDto.getFirstName() != null) user.setFirstName(updateUserDto.getFirstName());
        if (updateUserDto.getLastName() != null) user.setLastName(updateUserDto.getLastName());
        if (updateUserDto.getBirthdate() != null) user.setBirthdate(updateUserDto.getBirthdate());
        if (updateUserDto.getEmail() != null &&
                !updateUserDto.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("User already exists with email " + updateUserDto.getEmail());
            } else {
                user.setEmail(updateUserDto.getEmail());
            }
        }
        if (updateUserDto.getWeight() != null) user.setWeight(updateUserDto.getWeight());
        if (updateUserDto.getHeight() != null) user.setHeight(updateUserDto.getHeight());

        var savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

}
