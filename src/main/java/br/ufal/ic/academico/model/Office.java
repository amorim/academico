package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name="oid",nullable=false)
    private List<Course> courses;

    @OneToOne
    private Department department;

    private boolean isPostDegree;

    public boolean isPostDegree() {
        return isPostDegree;
    }



}
