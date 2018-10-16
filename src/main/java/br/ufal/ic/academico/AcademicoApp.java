package br.ufal.ic.academico;

import br.ufal.ic.academico.dao.*;
import br.ufal.ic.academico.exemplos.MyResource;
import br.ufal.ic.academico.exemplos.PersonExDAO;
import br.ufal.ic.academico.exemplos.PersonEx;
import br.ufal.ic.academico.model.*;
import br.ufal.ic.academico.resource.DepartmentResource;
import br.ufal.ic.academico.resource.StudentResource;
import br.ufal.ic.academico.resource.SubjectResource;
import br.ufal.ic.academico.resource.UtilResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Willy
 */
@Slf4j
@Getter
@Setter
public class AcademicoApp extends Application<ConfigApp> {

    public static void main(String[] args) throws Exception {
        new AcademicoApp().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ConfigApp> bootstrap) {
        log.info("initialize");
        bootstrap.addBundle(hibernate);
    }

    protected PersonDAO personDAO;
    protected SubjectDAO subjectDAO;
    protected DepartmentDAO departmentDAO;
    protected OfficeDAO officeDAO;
    protected CourseDAO courseDAO;

    @Override
    public void run(ConfigApp config, Environment environment) {
        
        final PersonExDAO dao = new PersonExDAO(hibernate.getSessionFactory());
        subjectDAO = new SubjectDAO(hibernate.getSessionFactory());
        departmentDAO = new DepartmentDAO(hibernate.getSessionFactory());
        officeDAO = new OfficeDAO(hibernate.getSessionFactory());
        courseDAO = new CourseDAO(hibernate.getSessionFactory());
        personDAO = new PersonDAO(hibernate.getSessionFactory());
        final UtilResource utilResource = new UtilResource(this);

        final MyResource resource = new MyResource(dao);

        final StudentResource sr = new StudentResource(this);
        final DepartmentResource dr = new DepartmentResource(this);
        final SubjectResource ssr = new SubjectResource(this);

        environment.jersey().register(sr);
        environment.jersey().register(utilResource);
        environment.jersey().register(resource);
        environment.jersey().register(dr);
        environment.jersey().register(ssr);
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(PersonEx.class, Subject.class, Course.class, Person.class, Office.class, Department.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}
