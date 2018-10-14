package br.ufal.ic.academico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@AllArgsConstructor

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isPostDegree;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Collection<Subject> subjects;


}
