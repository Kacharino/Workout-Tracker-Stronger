package at.kacharino.workouttrackerstronger.entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "workout_name")
    private String workoutName;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;



}
