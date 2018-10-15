package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.DepartmentDAO;
import br.ufal.ic.academico.dao.OfficeDAO;
import br.ufal.ic.academico.dao.SubjectDAO;
import br.ufal.ic.academico.model.*;
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

    private final AcademicoApp app;

    @GET
    @UnitOfWork
    public Response getAll() {

        log.info("getAll");

        return Response.ok(app.getSubjectDAO().getAll()).build();
    }

    @GET
    @Path("loadData")
    @UnitOfWork
    public Response loadData() {
        ArrayList<Subject> all = new ArrayList<>();
        log.info("loading all data into database");
        Subject s;

        Office o = new Office();
        o.setPostDegree(false);


        Course c = new Course();
        c.setName("Ciência da Computacão v2011");

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        o.setCourses(courses);
        app.getOfficeDAO().persist(o);

        s = new Subject();
        s.setName("Programação 1");
        s.setCode("COMP201");
        s.setCreditsNeeded((long)0);
        s.setAssociatedCredits((long)100);
        s.setDependencies(new ArrayList<>());
        s.setRequired(true);
        s.setCourse(c);
        all.add(s);
        app.getSubjectDAO().persist(s);

        s = new Subject();
        s.setName("Teste de Software");
        s.setCode("COMP259");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)100);
        ArrayList<Subject> thisdeps = new ArrayList<>();
        thisdeps.add(all.get(0));
        s.setDependencies(thisdeps);
        s.setRequired(true);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);

        all.add(s);

        s = new Subject();
        s.setName("Compiladores");
        s.setCode("COMP263");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)200);
        s.setDependencies(thisdeps);
        s.setRequired(true);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);
        all.add(s);

        s = new Subject();
        s.setName("Aprendizagem de Máquina");
        s.setCode("COMP339");
        s.setCreditsNeeded((long)100);
        s.setAssociatedCredits((long)200);
        s.setDependencies(thisdeps);
        s.setRequired(false);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);

        all.add(s);

        c.setSubjects(all);
        app.getCourseDAO().persist(c);




        Department d = new Department();
        d.setGraduationOffice(o);
        o.setDepartment(d);
        app.getDepartmentDAO().persist(d);

        Person p = new Person();
        p.setName("Lucas Amorim");
        p.setCredits(0);

        app.getPersonDAO().persist(p);

        return Response.noContent().build();
    }
}
