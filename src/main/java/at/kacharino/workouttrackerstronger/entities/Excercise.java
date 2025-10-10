package at.kacharino.workouttrackerstronger.entities;

import jakarta.persistence.*;

@Entity
public class Excercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "sets")
    private Integer sets;

    @Column(name = "repetitions")
    private Integer repetitions;


}
