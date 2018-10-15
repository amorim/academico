package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isPostDegree;


    @OneToMany()
    @JsonManagedReference()
    private Collection<Subject> subjects;


}
