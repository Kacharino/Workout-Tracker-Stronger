package at.kacharino.workouttrackerstronger.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;              // eigentliche Antwort (z. B. UserDto)
    private String error;        // Fehlermeldung (falls Fehler auftrat)
    private LocalDateTime timestamp; // wann die Antwort erzeuygt wurde

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .error(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
