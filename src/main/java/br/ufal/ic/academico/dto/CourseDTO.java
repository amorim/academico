package br.ufal.ic.academico.dto;

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
public class CourseDTO {
    private Long id;

    private String name;

    private boolean isPostDegree;

    private Collection<Subject> subjects;
}
