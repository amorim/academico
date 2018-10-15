package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.DepartmentDAO;
import br.ufal.ic.academico.dao.OfficeDAO;
import br.ufal.ic.academico.dao.SubjectDAO;
import br.ufal.ic.academico.model.Course;
import br.ufal.ic.academico.model.Department;
import br.ufal.ic.academico.model.Office;
import br.ufal.ic.academico.model.Subject;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("util")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class UtilResource {

    private final SubjectDAO subjectDAO;
    private final CourseDAO courseDAO;
    private final OfficeDAO officeDAO;
    private final DepartmentDAO departmentDAO;

    @GET
    @UnitOfWork
    public Response getAll() {

        log.info("getAll");

        return Response.ok(subjectDAO.getAll()).build();
    }

    @GET
    @Path("loadData")
    @UnitOfWork
    public Response loadData() {
        ArrayList<Subject> all = new ArrayList<>();
        log.info("loading all data into database");
        Subject s;


        Course c = new Course();
        c.setName("Ciência da Computacão v2011");
        c.setPostDegree(false);
        courseDAO.persist(c);

        s = new Subject();
        s.setName("Programação 1");
        s.setCode("COMP201");
        s.setCreditsNeeded((long)0);
        s.setAssociatedCredits((long)100);
        s.setDependencies(new ArrayList<>());
        s.setPostDegree(false);
        s.setRequired(true);
        subjectDAO.persist(s);
        s.setCourse(c);
        all.add(s);

        s = new Subject();
        s.setName("Teste de Software");
        s.setCode("COMP259");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)100);
        ArrayList<Subject> thisdeps = new ArrayList<>();
        thisdeps.add(all.get(0));
        s.setDependencies(thisdeps);
        s.setPostDegree(false);
        s.setRequired(true);
        s.setCourse(c);
        subjectDAO.persist(s);

        all.add(s);

        s = new Subject();
        s.setName("Compiladores");
        s.setCode("COMP263");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)200);
        s.setDependencies(thisdeps);
        s.setPostDegree(false);
        s.setRequired(true);
        s.setCourse(c);
        subjectDAO.persist(s);
        all.add(s);

        s = new Subject();
        s.setName("Aprendizagem de Máquina");
        s.setCode("COMP339");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)200);
        s.setDependencies(thisdeps);
        s.setPostDegree(false);
        s.setRequired(false);
        s.setCourse(c);
        subjectDAO.persist(s);

        all.add(s);

        c.setSubjects(all);
        courseDAO.persist(c);


        Office o = new Office();
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        o.setCourses(courses);
        officeDAO.persist(o);


        Department d = new Department();
        d.setGraduationOffice(o);
        o.setDepartment(d);
        departmentDAO.persist(d);



        return Response.noContent().build();
    }
}
