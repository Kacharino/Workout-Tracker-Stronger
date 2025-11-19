package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateUserDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturn201AndSuccessMessage_whenRegisterRequestDtoIsValid() throws Exception {
        var registerRequestDto = new RegisterRequestDto(
                "Maxi", "Maximo", "maxi@email.com", "MaximusPrime"
        );
        var json = objectMapper.writeValueAsString(registerRequestDto);

        doNothing().when(userService)
                .registerNewUser(any(RegisterRequestDto.class));

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("User registered successfully"));

        verify(userService).registerNewUser(any(RegisterRequestDto.class));
    }

    @Test
    void shouldReturn201AndSuccessMessage_whenLoginRequestDtoIsValid() throws Exception {
        var loginRequestDto = new LoginRequestDto(
                "maxi@email.com", "MaximusPrime"
        );
        var json = objectMapper.writeValueAsString(loginRequestDto);

        doNothing().when(userService).loginUser(any(LoginRequestDto.class));

        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("User login successful"));

        verify(userService).loginUser(any(LoginRequestDto.class));

    }

    @Test
    void shouldReturn200AndUserDtoWithSuccessMessage_whenUserIdWasFound() throws Exception {
        Long id = 1L;

        var userDto = new UserDto();
        userDto.setId(id);
        userDto.setFirstName("Maxi");
        userDto.setLastName("Maximus");
        userDto.setEmail("max@email.com");

        when(userService.getUserById(id)).thenReturn(userDto);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.email").value("max@email.com"));

        verify(userService).getUserById(id);
    }

    @Test
    void shouldReturn204AndSuccessMessage_whenUserIdWasFound() throws Exception {
        Long id = 1L;

        doNothing().when(userService).deleteUserById(id);

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.data").value("User deleted successfully"));

        verify(userService).deleteUserById(id);
    }

    @Test
    void shouldReturn200AndUserDtoWithSuccessMessage_whenUpdateUserDtoIsValid() throws Exception {
        Long id = 1L;

        var updateUserDto = new UpdateUserDto();
        updateUserDto.setBirthdate(LocalDate.of(2001, 9, 11));
        updateUserDto.setWeight(72.4);

        var json = objectMapper.writeValueAsString(updateUserDto);

        var returnedDto = new UserDto();
        returnedDto.setId(id);
        returnedDto.setBirthdate(LocalDate.of(2001, 9, 11));
        returnedDto.setWeight(72.4);

        when(userService.updateUserById(eq(id), any(UpdateUserDto.class)))
                .thenReturn(returnedDto);

        mockMvc.perform(patch("/users/{id}", id)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.birthdate").value("2001-09-11"))
                .andExpect(jsonPath("$.data.weight").value(72.4));

        verify(userService).updateUserById(eq(id), any(UpdateUserDto.class));
    }


}
