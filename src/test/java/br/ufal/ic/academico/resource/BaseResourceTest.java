package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.model.*;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class BaseResourceTest {


    ArrayList<Subject> subjects = new ArrayList<>();
    ArrayList<Office> offices = new ArrayList<>();
    ArrayList<Department> departments = new ArrayList<>();
    ArrayList<Person> people = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();

    void setUp() {
        AtomicLong idgen = new AtomicLong(1);
        log.info("setting up");

        log.info("loading all data into database");
        Subject s;

        Office o = new Office();
        o.setPostDegree(false);
        o.setId(idgen.getAndIncrement());
        offices.add(o);

        Course c = new Course();
        c.setName("Ciência da Computacão v2011");
        c.setId(idgen.getAndIncrement());
        c.setOffice(o);
        courses.add(c);
        o.setCourses(courses);

        Person p;
        p = new Person();
        p.setName("Willy");
        p.setTeacher(true);
        p.setPersonSubjects(new ArrayList<>());
        p.setId(idgen.getAndIncrement());
        people.add(p);
        s = new Subject();
        s.setName("Programação 1");
        s.setCode("COMP201");
        s.setCreditsNeeded((long) 0);
        s.setAssociatedCredits((long) 100);
        s.setDependencies(new ArrayList<>());
        s.setRequired(true);
        s.setCourse(c);
        s.setSubjectTeacher(p);
        s.setId(idgen.getAndIncrement());
        subjects.add(s);

        s = new Subject();
        s.setName("Teste de Software");
        s.setCode("COMP259");
        s.setCreditsNeeded((long) 100);
        s.setAssociatedCredits((long) 100);
        ArrayList<Subject> thisdeps = new ArrayList<>();
        thisdeps.add(subjects.get(0));
        s.setDependencies(thisdeps);
        s.setRequired(true);
        s.setId(idgen.getAndIncrement());
        s.setCourse(c);

        subjects.add(s);

        s = new Subject();
        s.setName("Compiladores");
        s.setCode("COMP263");
        s.setCreditsNeeded((long) 100);
        s.setAssociatedCredits((long) 200);
        s.setDependencies(thisdeps);
        s.setRequired(true);
        s.setCourse(c);
        s.setId(idgen.getAndIncrement());
        subjects.add(s);

        s = new Subject();
        s.setName("Aprendizagem de Máquina");
        s.setCode("COMP339");
        s.setCreditsNeeded((long) 100);
        s.setAssociatedCredits((long) 200);
        s.setDependencies(thisdeps);
        s.setRequired(false);
        s.setCourse(c);
        s.setId(idgen.getAndIncrement());
        subjects.add(s);

        c.setSubjects(subjects);


        Department d = new Department();
        d.setGraduationOffice(o);
        o.setDepartment(d);
        d.setId(idgen.getAndIncrement());
        departments.add(d);

        p = new Person();
        p.setName("Lucas Amorim");
        p.setCredits(0);
        p.setPersonSubjects(new ArrayList<>());
        people.add(p);
        p.setId(idgen.getAndIncrement());
    }
}
