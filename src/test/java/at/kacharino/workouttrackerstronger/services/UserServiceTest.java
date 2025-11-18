package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateUserDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.exceptions.InvalidCredentialsException;
import at.kacharino.workouttrackerstronger.exceptions.UserAlreadyExistsException;
import at.kacharino.workouttrackerstronger.exceptions.UserNotFoundException;
import at.kacharino.workouttrackerstronger.exceptions.ValidationException;
import at.kacharino.workouttrackerstronger.mappers.UserMapper;
import at.kacharino.workouttrackerstronger.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    // --------------------------------------------------------
    // registerNewUser()
    // --------------------------------------------------------

    @Test
    void shouldSaveNewUser_whenRegisterRequestDtoIsValid() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setFirstName("Max");
        registerRequestDto.setLastName("Maxi");
        registerRequestDto.setEmail("maxi@maxi.com");
        registerRequestDto.setPassword("maximusPrime");

        var user = new User();

        when(userRepository.findByEmail(registerRequestDto.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toEntity(registerRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(registerRequestDto.getPassword())).thenReturn("encodedPw");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        userService.registerNewUser(registerRequestDto);

        //Assert
        verify(passwordEncoder).encode(registerRequestDto.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowValidationException_whenFirstNameIsEmpty() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLastName("Maxi");
        registerRequestDto.setEmail("maxi@maxi.com");
        registerRequestDto.setPassword("maximusPrime");

        assertThrows(ValidationException.class,
                () -> userService.registerNewUser(registerRequestDto));
    }

    @Test
    void shouldThrowValidationException_whenFirstNameIsBlank() {
        var dto = new RegisterRequestDto();
        dto.setFirstName("   ");
        dto.setLastName("Test");
        dto.setEmail("test@test.com");
        dto.setPassword("12345");

        assertThrows(ValidationException.class, () -> userService.registerNewUser(dto));
    }

    @Test
    void shouldThrowValidationException_whenLastNameIsEmpty() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setFirstName("Maximilianovic");
        registerRequestDto.setEmail("maxi@maxi.com");
        registerRequestDto.setPassword("maximusPrime");

        assertThrows(ValidationException.class,
                () -> userService.registerNewUser(registerRequestDto));
    }

    @Test
    void shouldThrowValidationException_whenLastNameIsBlank() {
        var dto = new RegisterRequestDto();
        dto.setFirstName("Maxi");
        dto.setLastName("    ");
        dto.setEmail("test@test.com");
        dto.setPassword("12345");

        assertThrows(ValidationException.class, () -> userService.registerNewUser(dto));
    }

    @Test
    void shouldThrowValidationException_whenEmailIsEmpty() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLastName("Maxi");
        registerRequestDto.setFirstName("Maximilianovic");
        registerRequestDto.setPassword("maximusPrime");

        assertThrows(ValidationException.class,
                () -> userService.registerNewUser(registerRequestDto));
    }

    @Test
    void shouldThrowValidationException_whenEmailIsBlank() {
        var dto = new RegisterRequestDto();
        dto.setFirstName("Maxi");
        dto.setLastName("Maximilian");
        dto.setEmail("    ");
        dto.setPassword("12345");

        assertThrows(ValidationException.class,
                () -> userService.registerNewUser(dto));
    }

    @Test
    void shouldThrowValidationException_whenPasswordIsEmpty() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setLastName("Maxi");
        registerRequestDto.setFirstName("Maximilianovic");
        registerRequestDto.setEmail("maxi@maxi.com");

        assertThrows(ValidationException.class,
                () -> userService.registerNewUser(registerRequestDto));
    }

    @Test
    void shouldThrowValidationException_whenPasswordBlank() {
        var dto = new RegisterRequestDto();
        dto.setFirstName("Maxi");
        dto.setLastName("Maximilian");
        dto.setEmail("maxi@maxi.com");
        dto.setPassword("    ");

        assertThrows(ValidationException.class, () -> userService.registerNewUser(dto));
    }

    @Test
    void shouldThrowUserAlreadyExistsException_whenEmailAlreadyExists() {
        var registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setFirstName("Max");
        registerRequestDto.setLastName("Maxi");
        registerRequestDto.setEmail("maxi@maxi.com");
        registerRequestDto.setPassword("maximusPrime");

        var existingUser = new User();
        existingUser.setEmail("maxi@maxi.com");

        when(userRepository.findByEmail(registerRequestDto.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerNewUser(registerRequestDto));

    }

    // --------------------------------------------------------
    // loginUser()
    // --------------------------------------------------------


    @Test
    void shouldLoginUser_whenLoginDtoIsValid() {
        var loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("maxi@maxi.com");
        loginRequestDto.setPassword("maximusPrime");

        var user = new User();
        user.setPassword("maximusPrime");

        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())).thenReturn(true);

        userService.loginUser(loginRequestDto);

        verify(passwordEncoder).matches(loginRequestDto.getPassword(), user.getPassword());
    }

    @Test
    void shouldThrowUserNotFoundException_whenDtoEmailDoesNotMatchFromRepository() {
        var loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("maxi@maxi.com");
        loginRequestDto.setPassword("maximusPrime");

        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.loginUser(loginRequestDto));
    }

    @Test
    void shouldThrowInvalidCredentialsException_whenDtoPasswordDoesNotMatchFromRepository() {
        var loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("maxi@maxi.com");
        loginRequestDto.setPassword("maximusPrime");

        var user = new User();
        user.setPassword("maximusPrime");

        when(userRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,
                () -> userService.loginUser(loginRequestDto));
    }


    // --------------------------------------------------------
    // getUserById()
    // --------------------------------------------------------

    @Test
    void shouldReturnUserDto_whenUserExists() {
        //Arrange
        Long id = 1L;

        var user = new User();
        user.setId(id);
        user.setFirstName("Max");

        var userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName("Max");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        //Act
        var foundUser = userService.getUserById(id);
        //Assert
        assertEquals(userDto.getId(), foundUser.getId());
        assertEquals(userDto.getFirstName(), foundUser.getFirstName());
    }

    @Test
    void shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        //Arrange
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(id));

    }

    // --------------------------------------------------------
    // deleteUserById()
    // --------------------------------------------------------

    @Test
    void shouldDeleteUser_whenGivenIdExists() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);
        userService.deleteUserById(id);

        verify(userRepository).deleteById(id);

    }

    @Test
    void shouldThrowUserNotFoundException_whenGivenIdWasNotFound() {
        Long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUserById(id));
    }

    // --------------------------------------------------------
    // updateUserById()
    // --------------------------------------------------------

    @Test
    void shouldReturnUpdatedUser_whenUpdateUserDtoIsValid() {
        Long id = 1L;

        var updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("Felix");
        updateUserDto.setLastName("Felixano");
        updateUserDto.setEmail("felix@felix.com");
        updateUserDto.setBirthdate(LocalDate.of(2000, 9, 30));
        updateUserDto.setWeight(69.4);
        updateUserDto.setHeight(178.2);

        var user = new User();
        user.setEmail("felixNavidad@email.com");

        var savedUser = new User();
        UserDto userDto = new UserDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(updateUserDto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        userService.updateUserById(id, updateUserDto);

        verify(userRepository).save(user);
        verify(userMapper).toDto(savedUser);

    }

    @Test
    void shouldThrowUserAlreadyExistsException_whenUpdateUserDtoEmailExistsAlready() {
        Long id = 1L;

        var updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("felix@felix.com");

        var user = new User();
        user.setEmail("felixNavidad@email.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(updateUserDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.updateUserById(id, updateUserDto));
    }

    @Test
    void shouldReturnUpdatedUser_whenUpdateDtoEmailAndUserEmailAreTheSame() {
        Long id = 1L;

        var updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("felixNavidad@email.com");

        var user = new User();
        user.setEmail("felixNavidad@email.com");

        var savedUser = new User();
        UserDto userDto = new UserDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        userService.updateUserById(id, updateUserDto);
    }

    @Test
    void shouldReturnUpdatedUser_whenUpdateDtoEmailIsEmpty() {
        Long id = 1L;

        var updateUserDto = new UpdateUserDto();

        var user = new User();
        user.setEmail("felixNavidad@email.com");

        var savedUser = new User();
        UserDto userDto = new UserDto();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userDto);

        userService.updateUserById(id, updateUserDto);
    }


}