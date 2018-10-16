package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.AcademicoApp;
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
    @Path("loadData")
    @UnitOfWork
    public Response loadData() {
        ArrayList<Subject> all = new ArrayList<>(), all2 = new ArrayList<>();
        log.info("loading all data into database");
        Subject s;

        Office o = new Office();
        o.setPostDegree(false);

        Office o2 = new Office();
        o2.setPostDegree(true);


        Course c = new Course();
        c.setName("Ciência da Computacão v2011");

        Course c2 = new Course();
        c2.setName("Post degree CS");

        ArrayList<Course> courses = new ArrayList<>(), courses2 = new ArrayList<>();
        courses.add(c);
        courses2.add(c2);
        o.setCourses(courses);
        o2.setCourses(courses2);
        app.getOfficeDAO().persist(o);
        app.getOfficeDAO().persist(o2);
        Person p;
        p = new Person();
        p.setName("Willy");
        p.setTeacher(true);
        app.getPersonDAO().persist(p);

        s = new Subject();
        s.setName("Programação 1");
        s.setCode("COMP201");
        s.setCreditsNeeded((long) 0);
        s.setAssociatedCredits((long) 100);
        s.setDependencies(new ArrayList<>());
        s.setRequired(true);
        s.setCourse(c);
        s.setSubjectTeacher(p);
        all.add(s);
        app.getSubjectDAO().persist(s);

        s = new Subject();
        s.setName("PAA post grad");
        s.setCode("COMPP02");
        s.setCreditsNeeded((long) 0);
        s.setAssociatedCredits((long) 100);
        s.setDependencies(new ArrayList<>());
        s.setRequired(true);
        s.setCourse(c2);
        s.setSubjectTeacher(p);
        all2.add(s);
        app.getSubjectDAO().persist(s);

        s = new Subject();
        s.setName("Teste de Software");
        s.setCode("COMP259");
        s.setCreditsNeeded((long) 100);
        s.setAssociatedCredits((long) 100);
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
        s.setCreditsNeeded((long) 100);
        s.setAssociatedCredits((long) 200);
        s.setDependencies(thisdeps);
        s.setRequired(true);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);
        all.add(s);

        s = new Subject();
        s.setName("Aprendizagem de Máquina");
        s.setCode("COMP339");
        s.setCreditsNeeded((long) 0);
        s.setAssociatedCredits((long) 200);
        s.setDependencies(thisdeps);
        s.setRequired(false);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);

        all.add(s);

        c.setSubjects(all);
        c2.setSubjects(all2);
        c.setOffice(o);
        c2.setOffice(o2);
        app.getCourseDAO().persist(c);
        app.getCourseDAO().persist(c2);


        Department d = new Department();
        d.setGraduationOffice(o);
        d.setPostGraduationOffice(o2);
        o.setDepartment(d);
        o2.setDepartment(d);
        app.getDepartmentDAO().persist(d);
        app.getOfficeDAO().persist(o);
        app.getOfficeDAO().persist(o2);

        p = new Person();
        p.setName("Lucas Amorim");
        p.setCredits(0);

        app.getPersonDAO().persist(p);

        c = new Course();

        o = new Office();

        p = new Person();
        p.setName("Humanas Person");
        p.setTeacher(true);

        c.setName("Humanas v13");

        courses = new ArrayList<>();
        courses.add(c);
        o.setCourses(courses);
        app.getOfficeDAO().persist(o);
        c.setOffice(o);
        app.getPersonDAO().persist(p);
        app.getCourseDAO().persist(c);

        s = new Subject();
        s.setName("Humanas Subject");
        s.setCode("H01");
        s.setSubjectTeacher(p);
        s.setAssociatedCredits(0L);
        s.setCreditsNeeded(0L);
        s.setDependencies(new ArrayList<>());
        s.setRequired(false);
        s.setCourse(c);
        app.getSubjectDAO().persist(s);
        ArrayList<Subject> ss = new ArrayList<>();
        ss.add(s);
        c.setSubjects(ss);
        app.getCourseDAO().persist(c);

        d = new Department();
        d.setGraduationOffice(o);
        o.setDepartment(d);
        app.getDepartmentDAO().persist(d);
        app.getOfficeDAO().persist(o);


        p = new Person();
        p.setName("Post-degree student");
        p.setCredits(0);

        app.getPersonDAO().persist(p);


        return Response.noContent().build();
    }
}
