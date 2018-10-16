package br.ufal.ic.academico.resource;

import br.ufal.ic.academico.AcademicoApp;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("department")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {
    final AcademicoApp app;

    @GET()
    @Path("all")
    @UnitOfWork()
    public Response getDepartments() {
        return Response.ok().entity(app.getDepartmentDAO().getAll()).build();
    }
}
