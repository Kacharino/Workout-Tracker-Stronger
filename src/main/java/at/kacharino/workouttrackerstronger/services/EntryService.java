package at.kacharino.workouttrackerstronger.services;

import at.kacharino.workouttrackerstronger.dtos.CreateWorkoutEntryDto;
import at.kacharino.workouttrackerstronger.dtos.SetEntryResponseDto;
import at.kacharino.workouttrackerstronger.dtos.WorkoutEntryResponseDto;
import at.kacharino.workouttrackerstronger.entities.SetEntry;
import at.kacharino.workouttrackerstronger.entities.WorkoutEntry;
import at.kacharino.workouttrackerstronger.exceptions.AccessDeniedException;
import at.kacharino.workouttrackerstronger.exceptions.ExerciseNotFoundException;
import at.kacharino.workouttrackerstronger.exceptions.ValidationException;
import at.kacharino.workouttrackerstronger.exceptions.WorkoutNotFoundException;
import at.kacharino.workouttrackerstronger.repositories.ExerciseRepository;
import at.kacharino.workouttrackerstronger.repositories.SetEntryRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutEntryRepository;
import at.kacharino.workouttrackerstronger.repositories.WorkoutRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryService {
    private ExerciseRepository exerciseRepository;
    private WorkoutEntryRepository workoutEntryRepository;
    private WorkoutRepository workoutRepository;
    private SetEntryRepository setEntryRepository;

    @Transactional
    public Long addEntry(Long authenticatedUserId, Long workoutId, CreateWorkoutEntryDto createWorkoutEntryDto) {
        var workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with given ID does not exist."));

        if (!workout.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("Not allowed");
        }

        var exercise = exerciseRepository.findById(createWorkoutEntryDto.getExerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise with given ID does not exist."));

        // wo entry erzeugen
        WorkoutEntry workoutEntry = new WorkoutEntry();
        workoutEntry.setExercise(exercise);
        workoutEntry.setWorkout(workout);

        var savedWorkoutEntry = workoutEntryRepository.save(workoutEntry);

        // set entries erzeugen und alles speichern
        //TODO: die Sets vorher prüfen (sets darf nicht null und nicht leer sein)
        var setsDto = createWorkoutEntryDto.getSets();

        List<SetEntry> setEntries = new ArrayList<>();

        if (setsDto == null || setsDto.isEmpty()) {
            throw new ValidationException("Workout entry must contain at least one set");
        }

        int setNumber = 1;
        for (var s : setsDto) {

            if (s.getReps() <= 0) {
                throw new ValidationException("Reps must be greater than 0.");
            }
            if (s.getWeight() != null && s.getWeight().signum() < 0) {
                throw new ValidationException("Weight must not be negative.");
            }

            SetEntry setEntry = new SetEntry();
            setEntry.setReps(s.getReps());
            setEntry.setWeight(s.getWeight());
            setEntry.setSetNumber(setNumber);
            setEntry.setWorkoutEntry(savedWorkoutEntry);
            setEntries.add(setEntry);

            setNumber++;
        }

        setEntryRepository.saveAll(setEntries);
        return savedWorkoutEntry.getId();
    }


    public List<WorkoutEntryResponseDto> getWorkoutEntries(Long authenticatedUserId, Long workoutId) {
        var workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout with given ID does not exist."));

        if (!workout.getUser().getId().equals(authenticatedUserId)) {
            throw new AccessDeniedException("Not allowed");
        }

        // hier sind alle entries von einem Workout
        List<WorkoutEntry> workoutEntries = workoutEntryRepository.findByWorkoutId(workoutId);

        // wir returnen am Ende eine Liste aus WoEntryResponseDtos
        List<WorkoutEntryResponseDto> response = new ArrayList<>();


        // für jedes entry die sets laden
        //für jedes we muss ich:
        //SetEntryRespDto liste bauen und WoEntryRespDto
        for (var we : workoutEntries) {
            List<SetEntryResponseDto> setEntryResponseDtos = new ArrayList<>();
            List<SetEntry> setEntries = setEntryRepository.findByWorkoutEntryIdOrderBySetNumberAsc(we.getId());

            for (var se : setEntries) {
                var setEntryResponseDto = new SetEntryResponseDto();
                setEntryResponseDto.setReps(se.getReps());
                setEntryResponseDto.setWeight(se.getWeight());
                setEntryResponseDto.setSetNumber(se.getSetNumber());
                setEntryResponseDtos.add(setEntryResponseDto);
            }

            var workoutEntryResponseDto = new WorkoutEntryResponseDto();
            workoutEntryResponseDto.setEntryId(we.getId());
            workoutEntryResponseDto.setExerciseId(we.getExercise().getId());
            workoutEntryResponseDto.setExerciseName(we.getExercise().getName());
            workoutEntryResponseDto.setCategory(we.getExercise().getCategory());
            workoutEntryResponseDto.setSets(setEntryResponseDtos);
            response.add(workoutEntryResponseDto);

        }
        return response;
    }
}






