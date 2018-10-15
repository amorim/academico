package br.ufal.ic.academico.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Office graduationOffice;

    @OneToOne
    private Office postGraduationOffice;
}
