package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
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

    @ManyToMany
    private Collection<Subject> dependencies;

    @ManyToOne()
    @JsonBackReference()
    private Course course;

    @ManyToMany
    private List<Person> enrolledPeople;


}
