package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Long id);
}
