package br.ufal.ic.academico;

import br.ufal.ic.academico.dao.CourseDAO;
import br.ufal.ic.academico.dao.DepartmentDAO;
import br.ufal.ic.academico.dao.OfficeDAO;
import br.ufal.ic.academico.dao.SubjectDAO;
import br.ufal.ic.academico.exemplos.MyResource;
import br.ufal.ic.academico.exemplos.PersonDAO;
import br.ufal.ic.academico.exemplos.PersonEx;
import br.ufal.ic.academico.model.*;
import br.ufal.ic.academico.resource.UtilResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Willy
 */
@Slf4j
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

    @Override
    public void run(ConfigApp config, Environment environment) {
        
        final PersonDAO dao = new PersonDAO(hibernate.getSessionFactory());
        final SubjectDAO subjectDAO = new SubjectDAO(hibernate.getSessionFactory());
        final DepartmentDAO departmentDAO = new DepartmentDAO(hibernate.getSessionFactory());
        final OfficeDAO officeDAO = new OfficeDAO(hibernate.getSessionFactory());
        final CourseDAO courseDAO = new CourseDAO(hibernate.getSessionFactory());
        final UtilResource utilResource = new UtilResource(subjectDAO, courseDAO, officeDAO, departmentDAO);

        final MyResource resource = new MyResource(dao);

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
