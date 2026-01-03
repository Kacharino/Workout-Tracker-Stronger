package at.kacharino.workouttrackerstronger.repositories;

import at.kacharino.workouttrackerstronger.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
