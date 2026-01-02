package at.kacharino.workouttrackerstronger.exceptions;

import at.kacharino.workouttrackerstronger.security.JwtAuthenticationFilter;
import at.kacharino.workouttrackerstronger.security.UserDetailsServiceImpl;
import at.kacharino.workouttrackerstronger.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DummyController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({GlobalExceptionHandler.class, DummyController.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    // -------------------------
    // USER NOT FOUND (404)
    // -------------------------
    @Test
    void shouldReturn404_whenUserNotFoundIsThrown() throws Exception {
        mockMvc.perform(get("/test-user-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // USER ALREADY EXISTS (409)
    // -------------------------
    @Test
    void shouldReturn409_whenUserAlreadyExistsIsThrown() throws Exception {
        mockMvc.perform(get("/test-user-already-exists"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("User already exists"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // WORKOUT NOT FOUND (404)
    // -------------------------
    @Test
    void shouldReturn404_whenWorkoutNotFoundIsThrown() throws Exception {
        mockMvc.perform(get("/test-workout-not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Workout not found"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // NO CONTENT (204)
    // -------------------------
    @Test
    void shouldReturn204_whenNoContentIsThrown() throws Exception {
        mockMvc.perform(get("/test-no-content"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").value("No content available"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // ILLEGAL ARGUMENT (409)
    // -------------------------
    @Test
    void shouldReturn409_whenIllegalArgumentIsThrown() throws Exception {
        mockMvc.perform(get("/test-illegal-argument"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Illegal argument"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // VALIDATION EXCEPTION (400)
    // -------------------------
    @Test
    void shouldReturn400_whenValidationExceptionIsThrown() throws Exception {
        mockMvc.perform(get("/test-validation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // INVALID CREDENTIALS (401)
    // -------------------------
    @Test
    void shouldReturn401_whenInvalidCredentialsIsThrown() throws Exception {
        mockMvc.perform(get("/test-invalid-credentials"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // -------------------------
    // GENERIC EXCEPTION (500)
    // -------------------------
    @Test
    void shouldReturn500_whenUnexpectedExceptionIsThrown() throws Exception {
        mockMvc.perform(get("/test-generic"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected error: something went wrong"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void shouldReturn400WithRegisterMessage_whenMalformedJsonForRegister() throws Exception {
        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content("\"INVALID\"")) // simuliert kaputtes JSON
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("Your JSON body is empty or malformed.")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WithLoginMessage_whenMalformedJsonForLogin() throws Exception {
        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content("\"INVALID\""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("Your JSON body is empty or malformed.")))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WithGenericMessage_whenMalformedJsonForOtherEndpoints() throws Exception {
        mockMvc.perform(post("/any-other-endpoint")
                        .contentType("application/json")
                        .content("\"INVALID\""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Request body is missing or malformed."))
                .andExpect(jsonPath("$.timestamp").exists());
    }

}