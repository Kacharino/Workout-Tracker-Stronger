package at.kacharino.workouttrackerstronger.controller;

import at.kacharino.workouttrackerstronger.dtos.UpdateWorkoutDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutDto;
import at.kacharino.workouttrackerstronger.services.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkoutController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkoutControllerTest {

    @MockBean
    WorkoutService workoutService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldReturn201AndResultWorkoutDto_whenWorkoutDtoIsValid() throws Exception {

        var workoutDto = new WorkoutDto();
        workoutDto.setId(1L);
        workoutDto.setWorkoutName("Push");
        workoutDto.setDate(LocalDate.of(2000, 11, 20));
        workoutDto.setDuration(LocalTime.of(1, 2, 3));
        workoutDto.setUserId(2L);

        var json = objectMapper.writeValueAsString(workoutDto);

        when(workoutService.createWorkout(any(WorkoutDto.class)))
                .thenReturn(workoutDto);

        mockMvc.perform(post("/workouts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.workoutName").value("Push"))
                .andExpect(jsonPath("$.data.date").value("2000-11-20"))
                .andExpect(jsonPath("$.data.duration").value("01:02:03"))
                .andExpect(jsonPath("$.data.userId").value(2));

        verify(workoutService).createWorkout(any(WorkoutDto.class));
    }

    @Test
    void shouldReturnRequestedWorkoutDto_whenWorkoutWasFoundById() throws Exception {

        var workoutDto = new WorkoutDto();
        workoutDto.setId(1L);
        workoutDto.setWorkoutName("Push");
        workoutDto.setDate(LocalDate.of(2000, 11, 20));
        workoutDto.setDuration(LocalTime.of(1, 2, 3));
        workoutDto.setUserId(2L);

        when(workoutService.getWorkoutById(1L)).thenReturn(workoutDto);

        mockMvc.perform(get("/workouts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.workoutName").value("Push"))
                .andExpect(jsonPath("$.data.date").value("2000-11-20"))
                .andExpect(jsonPath("$.data.duration").value("01:02:03"))
                .andExpect(jsonPath("$.data.userId").value(2L));

        verify(workoutService).getWorkoutById(1L);
    }

    @Test
    void shouldReturnListOfWorkouts_whenWorkoutsWasFoundWithUserId() throws Exception {
        Long userId = 1L;

        var workoutDto1 = new WorkoutDto();
        workoutDto1.setId(10L);
        workoutDto1.setWorkoutName("Push");

        var workoutDto2 = new WorkoutDto();
        workoutDto2.setId(20L);
        workoutDto2.setWorkoutName("Pull");

        var workoutDtos = List.of(workoutDto1, workoutDto2);

        when(workoutService.getWorkoutsByUserId(userId)).thenReturn(workoutDtos);

        mockMvc.perform(get("/workouts/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(10))
                .andExpect(jsonPath("$.data[0].workoutName").value("Push"))
                .andExpect(jsonPath("$.data[1].id").value(20))
                .andExpect(jsonPath("$.data[1].workoutName").value("Pull"))
                        .andExpect(jsonPath("$.data", hasSize(2)));

        verify(workoutService).getWorkoutsByUserId(userId);
    }

    @Test
    void shouldReturnUpdatedWorkoutDto_whenUpdateWorkoutDtoIsValid() throws Exception {
        Long id = 1L;

        var updateWorkoutDto = new UpdateWorkoutDto();
        updateWorkoutDto.setWorkoutName("Pull in GYM");
        updateWorkoutDto.setDuration(LocalTime.of(2, 3, 4));

        var json = objectMapper.writeValueAsString(updateWorkoutDto);

        var workoutDto = new WorkoutDto();
        workoutDto.setId(id);
        workoutDto.setWorkoutName("Pull in GYM");
        workoutDto.setDuration(LocalTime.of(2, 3, 4));

        when(workoutService.updateWorkoutById(eq(id), any(UpdateWorkoutDto.class))).thenReturn(workoutDto);

        mockMvc.perform(patch("/workouts/{id}", id)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.workoutName").value("Pull in GYM"))
                .andExpect(jsonPath("$.data.duration").value("02:03:04"));

        verify(workoutService).updateWorkoutById(eq(id), any(UpdateWorkoutDto.class));
    }

    @Test
    void shouldReturnSuccessMessage_whenWorkoutWasSuccessfullyDeleted() throws Exception {
        Long id = 1L;

        String message = "User deleted successfully";

        when(workoutService.deleteWorkoutById(id)).thenReturn(message);

        mockMvc.perform(delete("/workouts/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.data").value(message));

        verify(workoutService).deleteWorkoutById(id);


    }
}
