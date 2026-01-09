package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.dtos.BestWeightPerExerciseDto;
import at.kacharino.workouttrackerstronger.entities.SetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {
    List<SetEntry> findByWorkoutEntryIdOrderBySetNumberAsc(Long workoutEntryId);

    @Query("""
    SELECT new at.kacharino.workouttrackerstronger.dtos.BestWeightPerExerciseDto(
        e.id,
        e.name,
        MAX(se.weight)
    )
    FROM SetEntry se
    JOIN se.workoutEntry we
    JOIN we.workout w
    JOIN we.exercise e
    WHERE w.user.id = :userId
      AND se.weight IS NOT NULL
    GROUP BY e.id, e.name
    ORDER BY e.name
    """)
    List<BestWeightPerExerciseDto> findBestWeightPerExerciseByUserId(@Param("userId") Long userId);

}
