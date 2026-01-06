package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.SetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {
    List<SetEntry> findByWorkoutEntryIdOrderBySetNumberAsc(Long workoutEntryId);
}
