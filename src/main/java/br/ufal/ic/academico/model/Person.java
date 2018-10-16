package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isTeacher;

    @JsonIgnore
    @ManyToMany
    private List<Subject> personSubjects;

    private int credits;

    @ManyToOne
    @JsonIgnore
    private Course personCourse;

    @OneToMany(mappedBy = "subjectTeacher")
    @JsonIgnore
    private List<Subject> teacherSubjects;
}
