package br.ufal.ic.academico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long associatedCredits;

    @Column(nullable = false)
    private Long creditsNeeded;

    @Column(nullable = false)
    private boolean isRequired;

    @Column(nullable = false)
    private boolean isPostDegree;

    @OneToMany
    private Collection<Subject> dependencies;

    @ManyToOne
    private Course course;


}
