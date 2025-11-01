package at.kacharino.workouttrackerstronger.exceptions;

public class WorkoutNotFoundException extends RuntimeException {
    public WorkoutNotFoundException(String message) {
        super(message);
    }
}