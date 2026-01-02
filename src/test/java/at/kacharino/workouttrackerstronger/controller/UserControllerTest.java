package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.LoginRequestDto;
import at.kacharino.workouttrackerstronger.dtos.RegisterRequestDto;
import at.kacharino.workouttrackerstronger.dtos.UpdateUserDto;
import at.kacharino.workouttrackerstronger.dtos.UserDto;
import at.kacharino.workouttrackerstronger.security.AuthUtil;
import at.kacharino.workouttrackerstronger.security.UserDetailsServiceImpl;
import at.kacharino.workouttrackerstronger.services.JwtService;
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

    @MockBean
    AuthUtil authUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtService jwtService;

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
        var loginRequestDto = new LoginRequestDto("maxi@email.com", "MaximusPrime");

        String token = "superToken";

        when(userService.loginUser(any(LoginRequestDto.class))).thenReturn(token);

        var json = objectMapper.writeValueAsString(loginRequestDto);
        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value(token));

        verify(userService).loginUser(any(LoginRequestDto.class));
    }

    @Test
    void shouldReturn200AndUserDtoWithSuccessMessage_whenUserIdWasFound() throws Exception {
        Long authenticatedUserId = 1L;

        var userDto = new UserDto();
        userDto.setId(authenticatedUserId);
        userDto.setFirstName("Maxi");
        userDto.setLastName("Maximus");
        userDto.setEmail("max@email.com");

        when(authUtil.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(userService.getUserById(authenticatedUserId)).thenReturn(userDto);

        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(authenticatedUserId))
                .andExpect(jsonPath("$.data.email").value("max@email.com"));

        verify(userService).getUserById(authenticatedUserId);
    }

    @Test
    void shouldReturn204AndSuccessMessage_whenUserIdWasFound() throws Exception {
        Long authenticatedUserId = 1L;

        when(authUtil.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        doNothing().when(userService).deleteUserById(authenticatedUserId);

        mockMvc.perform(delete("/users/me"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.data").value("User deleted successfully"));

        verify(userService).deleteUserById(authenticatedUserId);
        verify(authUtil).getAuthenticatedUserId();
    }

    @Test
    void shouldReturn200AndUserDtoWithSuccessMessage_whenUpdateUserDtoIsValid() throws Exception {
        Long authenticatedUserId = 1L;

        var updateUserDto = new UpdateUserDto();
        updateUserDto.setBirthdate(LocalDate.of(2001, 9, 11));
        updateUserDto.setWeight(72.4);


        var returnedDto = new UserDto();
        returnedDto.setId(authenticatedUserId);
        returnedDto.setFirstName("Maxi");
        returnedDto.setLastName("Maximus");
        returnedDto.setEmail("max@email.com");
        returnedDto.setBirthdate(LocalDate.of(2001, 9, 11));
        returnedDto.setWeight(72.4);
        returnedDto.setHeight(177.0);

        when(authUtil.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(userService.updateUserById(eq(authenticatedUserId), any(UpdateUserDto.class)))
                .thenReturn(returnedDto);

        var json = objectMapper.writeValueAsString(updateUserDto);
        mockMvc.perform(patch("/users/me")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("Maxi"))
                .andExpect(jsonPath("$.data.lastName").value("Maximus"))
                .andExpect(jsonPath("$.data.email").value("max@email.com"))
                .andExpect(jsonPath("$.data.birthdate").value("2001-09-11"))
                .andExpect(jsonPath("$.data.weight").value(72.4))
                .andExpect(jsonPath("$.data.height").value(177.0));


        verify(userService).updateUserById(eq(authenticatedUserId), any(UpdateUserDto.class));
        verify(authUtil).getAuthenticatedUserId();
    }


}
