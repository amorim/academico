package br.ufal.ic.academico.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isTeacher;

    @ManyToMany
    private List<Subject> personSubjects;

    private int credits;

    @ManyToOne
    private Course personCourse;
}
