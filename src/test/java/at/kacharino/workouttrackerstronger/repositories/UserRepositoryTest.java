package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {
    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void shouldFindEmail_whenEmailIsValid() {
        User user = new User();
        user.setFirstName("Maxi");
        user.setLastName("Maximus");
        user.setEmail("maxi@email.com");
        user.setPassword("MaximusPrime");

        testEntityManager.persist(user);
        testEntityManager.flush();

        var optionalUser = userRepository.findByEmail(user.getEmail());

        assertTrue(optionalUser.isPresent());
        assertEquals(user.getEmail(), optionalUser.get().getEmail());
        assertEquals(user.getId(), optionalUser.get().getId());

    }

    @Test
    void shouldFindUserById_whenUserExists() {
        User user = new User();
        user.setFirstName("Maxi");
        user.setLastName("Maximus");
        user.setEmail("maxi@email.com");
        user.setPassword("MaximusPrime");

        testEntityManager.persist(user);
        testEntityManager.flush();

        var optionalUser = userRepository.findById(user.getId());

        assertTrue(optionalUser.isPresent());
        assertEquals(user.getId(), optionalUser.get().getId());
    }

    @Test
    void shouldSaveUser_whenUserIsValid() {
        User user = new User();
        user.setFirstName("Maxi");
        user.setLastName("Maximus");
        user.setEmail("maxi@email.com");
        user.setPassword("MaximusPrime");

        var savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getId(), savedUser.getId());

    }

    @Test
    void shouldReturnAllUsers_whenUsersExist() {
        var user1 = new User();
        user1.setFirstName("A");
        user1.setLastName("Alpha");
        user1.setEmail("a@mail.com");
        user1.setPassword("pw");

        var user2 = new User();
        user2.setFirstName("B");
        user2.setLastName("Beta");
        user2.setEmail("b@mail.com");
        user2.setPassword("pw");

        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
        testEntityManager.flush();

        var users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void shouldDeleteUserById_whenIdIsValid() {
        var user = new User();
        user.setFirstName("Maxi");
        user.setLastName("Maximus");
        user.setEmail("maxi@email.com");
        user.setPassword("MaximusPrime");

        var savedUser = testEntityManager.persistFlushFind(user);

        userRepository.deleteById(savedUser.getId());
        testEntityManager.flush();

        var result = userRepository.findById(savedUser.getId());

        assertTrue(result.isEmpty());
    }







}