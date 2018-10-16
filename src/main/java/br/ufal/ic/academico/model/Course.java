package br.ufal.ic.academico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="oid", insertable = false, updatable = false)
    @JsonBackReference(value="office")
    private Office office;

    @OneToMany()
    @JsonManagedReference(value="course")
    private Collection<Subject> subjects;

    @JsonIgnore
    public boolean isPostDegree() {
        return office.isPostDegree();
    }

}
