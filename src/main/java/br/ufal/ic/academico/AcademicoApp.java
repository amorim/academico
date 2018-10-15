package br.ufal.ic.academico;

import br.ufal.ic.academico.dao.*;
import br.ufal.ic.academico.exemplos.MyResource;
import br.ufal.ic.academico.exemplos.PersonExDAO;
import br.ufal.ic.academico.exemplos.PersonEx;
import br.ufal.ic.academico.model.*;
import br.ufal.ic.academico.resource.StudentResource;
import br.ufal.ic.academico.resource.UtilResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Willy
 */
@Slf4j
@Getter
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

    private PersonDAO personDAO;
    private SubjectDAO subjectDAO;
    private DepartmentDAO departmentDAO;
    private OfficeDAO officeDAO;
    private CourseDAO courseDAO;

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

        environment.jersey().register(sr);
        environment.jersey().register(utilResource);
        environment.jersey().register(resource);
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(PersonEx.class, Subject.class, Course.class, Person.class, Office.class, Department.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}
