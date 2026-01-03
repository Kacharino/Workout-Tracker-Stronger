package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.entities.Exercise;
import at.kacharino.workouttrackerstronger.security.UserDetailsServiceImpl;
import at.kacharino.workouttrackerstronger.services.ExerciseService;
import at.kacharino.workouttrackerstronger.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(ExerciseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExerciseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExerciseService exerciseService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldReturnListOfExercisesAnd200_whenGetMappingForExercisesIsAsked() throws Exception {
        var ex1 = new Exercise(1L, "Bench Press", "Pressing Chest on a Bench", "Chest");
        var ex2 = new Exercise(2L, "Pull Up", "Pulling yourself up on the Rail", "Back");

        when(exerciseService.getAllExercises()).thenReturn(List.of(ex1, ex2));

        mockMvc.perform(get("/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Bench Press"));

        verify(exerciseService).getAllExercises();
    }


}
