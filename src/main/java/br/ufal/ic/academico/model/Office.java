package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference(value="office")
    @JoinColumn(name="oid")
    private List<Course> courses;

    @OneToOne
    @JsonBackReference(value="department")
    private Department department;

    private boolean isPostDegree;

    public boolean isPostDegree() {
        return isPostDegree;
    }



}
