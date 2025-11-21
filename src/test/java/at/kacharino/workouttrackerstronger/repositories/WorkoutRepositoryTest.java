package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.User;
import at.kacharino.workouttrackerstronger.entities.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class WorkoutRepositoryTest {
    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User testUser;

    @BeforeEach
    void cleanDatabase() {
        workoutRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        // User vorbereiten
        testUser = new User();
        testUser.setFirstName("Maxi");
        testUser.setLastName("Maximus");
        testUser.setEmail("max@email.com");
        testUser.setPassword("MaximusPrime");
        testUser.setBirthdate(LocalDate.of(2000, 1, 1));
        testUser.setHeight(183.5);
        testUser.setWeight(84.9);

        testEntityManager.persist(testUser);
        testEntityManager.flush();
    }

    @Test
    void shouldSaveAndFindWorkoutById() {
        var workout = new Workout();
        workout.setWorkoutName("Push");
        workout.setDate(LocalDate.now());
        workout.setDuration(LocalTime.of(1, 30));
        workout.setUser(testUser);

        testEntityManager.persist(workout);
        testEntityManager.flush();

        var foundWorkout = workoutRepository.findById(workout.getId());

        assertTrue(foundWorkout.isPresent());
        assertEquals(workout.getId(), foundWorkout.get().getId());

    }

    @Test
    void shouldReturnAllWorkouts_whenWorkoutsExists(){
        var workout = new Workout();
        workout.setWorkoutName("Push");
        workout.setDate(LocalDate.now());
        workout.setDuration(LocalTime.of(1, 11));
        workout.setUser(testUser);

        var workout2 = new Workout();
        workout2.setWorkoutName("Pull");
        workout2.setDate(LocalDate.now());
        workout2.setDuration(LocalTime.of(2, 5));
        workout2.setUser(testUser);

        var workout3 = new Workout();
        workout3.setWorkoutName("Legs");
        workout3.setDate(LocalDate.now());
        workout3.setDuration(LocalTime.of(1, 49));
        workout3.setUser(testUser);

        testEntityManager.persist(workout);
        testEntityManager.persist(workout2);
        testEntityManager.persist(workout3);
        testEntityManager.flush();

        var allWorkouts = workoutRepository.findAll();

        assertEquals(3, allWorkouts.size());
    }

    @Test
    void shouldFindWorkoutByUserId_whenUserHasWorkoutsSaved() {
        var workout = new Workout();
        workout.setWorkoutName("Push");
        workout.setDate(LocalDate.now());
        workout.setDuration(LocalTime.of(1, 11));
        workout.setUser(testUser);

        var workout2 = new Workout();
        workout2.setWorkoutName("Pull");
        workout2.setDate(LocalDate.now());
        workout2.setDuration(LocalTime.of(2, 5));
        workout2.setUser(testUser);

        testEntityManager.persist(workout);
        testEntityManager.persist(workout2);
        testEntityManager.flush();

        var foundWorkouts = workoutRepository.findByUserId(testUser.getId());

        assertEquals(2, foundWorkouts.size());
    }

    @Test
    void shouldReturnWorkoutWithAttachedUser_whenUserIsAttached() {
        var workout = new Workout();
        workout.setWorkoutName("Push");
        workout.setDate(LocalDate.now());
        workout.setDuration(LocalTime.of(1, 11));
        workout.setUser(testUser);

        testEntityManager.persist(workout);
        testEntityManager.flush();

        var saved = workoutRepository.findById(workout.getId()).get();

        assertNotNull(saved);
        assertEquals(testUser.getEmail(), saved.getUser().getEmail());
    }

}
