package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
