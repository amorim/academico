package br.ufal.ic.academico.dto;

import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonDTO {
    private Long id;
    private String name;
    private boolean isTeacher;
    private List<Subject> personSubjects;
    private int credits;
    private Course personCourse;
}
