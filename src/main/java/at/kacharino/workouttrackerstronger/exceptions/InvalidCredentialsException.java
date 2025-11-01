package at.kacharino.workouttrackerstronger.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
