package at.kacharino.workouttrackerstronger.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Templates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
