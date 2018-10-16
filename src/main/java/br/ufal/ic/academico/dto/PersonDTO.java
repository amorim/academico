package br.ufal.ic.academico.dto;

import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Subject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class PersonDTO {
    private Long id;
    private String name;
    private boolean isTeacher;
    private List<Subject> personSubjects;
    private int credits;
    private Course personCourse;
}
