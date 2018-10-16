package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.dao.*;

import static org.mockito.Mockito.mock;

public class MockDAO {
    static PersonDAO PERSONDAO_MOCK = mock(PersonDAO.class);

    static CourseDAO COURSEDAO_MOCK = mock(CourseDAO.class);

    static OfficeDAO OFFICEDAO_MOCK = mock(OfficeDAO.class);

    static SubjectDAO SUBJECTDAO_MOCK = mock(SubjectDAO.class);

    static DepartmentDAO DEPARTMENTDAO_MOCK = mock(DepartmentDAO.class);

}
