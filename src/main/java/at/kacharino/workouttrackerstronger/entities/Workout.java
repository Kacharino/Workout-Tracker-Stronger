package at.kacharino.workouttrackerstronger.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "workout_name")
    private String workoutName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "duration")
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
