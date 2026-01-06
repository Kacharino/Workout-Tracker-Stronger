package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findByWorkoutId(Long workoutId);
}
