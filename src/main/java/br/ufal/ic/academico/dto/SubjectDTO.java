package br.ufal.ic.academico.dto;

import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectDTO {
    private Long id;
    private String code;
    private String name;
    private Long associatedCredits;
    private Long creditsNeeded;
    private boolean isRequired;
    private boolean isPostDegree;
    private Collection<Subject> dependencies;
    private Course course;
}
