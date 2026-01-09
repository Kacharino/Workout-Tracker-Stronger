package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.BestWeightPerExerciseDto;
import at.kacharino.workouttrackerstronger.repositories.SetEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProgressService {


    private final SetEntryRepository setEntryRepository;

    public List<BestWeightPerExerciseDto> getBestWeightPerExercise(Long authenticatedUserId) {
        return setEntryRepository.findBestWeightPerExerciseByUserId(authenticatedUserId);

    }
}