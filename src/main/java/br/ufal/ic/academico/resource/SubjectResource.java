package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.AcademicoApp;
import br.ufal.ic.academico.model.Subject;
import br.ufal.ic.academico.util.RestResponse;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("subject")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {
    final AcademicoApp app;

    @Path("/{id}")
    @GET
    @UnitOfWork()
    public Response subjectQuery(@PathParam("id") Long id) {
        Subject s = app.getSubjectDAO().get(id);
        if (s == null)
            return Response.status(Response.Status.NOT_FOUND).entity(new RestResponse(Response.Status.NOT_FOUND.getStatusCode(), "Subject not found")).build();
        return Response.ok().entity(s).build();
    }
}
