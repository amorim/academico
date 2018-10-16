package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.dto.PersonDTO;
import br.ufal.ic.academico.model.Person;
import br.ufal.ic.academico.model.Subject;
import br.ufal.ic.academico.util.RestResponse;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("student")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    final AcademicoApp app;

    @GET
    @UnitOfWork
    @Path("/{id}")
    public Response getInfo(@PathParam("id") Long id) {
        Person p = app.getPersonDAO().get(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new RestResponse(Response.Status.NOT_FOUND.getStatusCode(), "Student not found")).build();
        }
        if (p.isTeacher())
            return Response.status(Response.Status.NOT_FOUND).entity(new RestResponse(Response.Status.NOT_FOUND.getStatusCode(),"This id becomes to a teacher")).build();
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(p.getName());
        personDTO.setId(p.getId());
        personDTO.setCredits(p.getCredits());
        personDTO.setPersonSubjects(p.getPersonSubjects());
        personDTO.setTeacher(false);
        return Response.ok().entity(personDTO).build();
    }


    @POST
    @UnitOfWork
    @Path("/{id}/enroll/subject/{subjectId}")
    public Response enroll(@PathParam("id") Long id, @PathParam("subjectId") Long subjectId) {
        Person student = app.getPersonDAO().get(id);
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new RestResponse(Response.Status.NOT_FOUND.getStatusCode(), "Student not found")).build();
        if (student.isTeacher())
            return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("This id becomes to a teacher. Enrollment is only allowed for students.")).build();
        Subject s = app.getSubjectDAO().get(subjectId);
        if (s == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("Subject not found")).build();
        if (student.getPersonCourse() != null)
            if (!student.getPersonCourse().getId().equals(s.getCourse().getId()))
                return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("Student course doesn\'t match subject course")).build();
        for (Subject subject : student.getPersonSubjects())
            if (s.getId().equals(subject.getId()))
                return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("Student is already enrolled to this subject.")).build();
        if (!s.isPostDegree() && student.getPersonCourse() != null && student.getPersonCourse().isPostDegree())
            return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("Post-degree student cannot enroll in an undergraduation subject")).build();
        if (student.getPersonCourse() != null && !student.getPersonCourse().isPostDegree() && s.isPostDegree())
            if (student.getCredits() < 170)
                return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("For enrolling in a post-graduation subject, you need at least 170 credits.")).build();
        if (s.getCreditsNeeded() > student.getCredits())
            return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("Not enough credits to enroll in this subject")).build();
        Collection<Subject> deps = s.getDependencies();
        if (!student.getPersonSubjects().containsAll(deps))
            return Response.status(Response.Status.BAD_REQUEST).entity(new RestResponse("You have to complete pending required subjects before enrolling into this subject")).build();
        log.info("student can now enroll");
        student.getPersonSubjects().add(s);
        student.setPersonCourse(s.getCourse());
        student.setCredits((int)(student.getCredits() + s.getAssociatedCredits()));
        app.getPersonDAO().persist(student);
        return Response.ok().entity(new RestResponse(Response.Status.OK.getStatusCode(), "Student is now enrolled")).build();
    }



}
