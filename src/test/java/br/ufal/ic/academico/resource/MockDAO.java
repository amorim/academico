package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.dao.*;

import static org.mockito.Mockito.mock;

public class MockDAO {
    public static PersonDAO PERSONDAO_MOCK = mock(PersonDAO.class);

    public static CourseDAO COURSEDAO_MOCK = mock(CourseDAO.class);

    public static OfficeDAO OFFICEDAO_MOCK = mock(OfficeDAO.class);

    public static SubjectDAO SUBJECTDAO_MOCK = mock(SubjectDAO.class);

    public static DepartmentDAO DEPARTMENTDAO_MOCK = mock(DepartmentDAO.class);

}
