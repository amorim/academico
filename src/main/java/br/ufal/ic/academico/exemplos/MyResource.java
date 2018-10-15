package br.ufal.ic.academico.exemplos;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Willy
 */
@Path("exemplos")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {
    
    private final PersonExDAO personDAO;
    
    @GET
    @UnitOfWork
    public Response getAll() {
        
        log.info("getAll");
        
        return Response.ok("[]").build();
    }
    
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") Long id) {
        
        log.info("getById: id={}", id);
        
        PersonEx p = personDAO.get(id);
        
        return Response.ok(p).build();
    }

    @POST
    @UnitOfWork
    @Consumes("application/json")
    public Response save(PersonDTO entity) {
        
        log.info("save: {}", entity);
        
        PersonEx p = new PersonEx(entity.getName());
        p.setScore(entity.getNumber());
        
        return Response.ok(personDAO.persist(p)).build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    @Consumes("application/json")
    public Response update(@PathParam("id") Long id, PersonDTO entity) {
        
        log.info("update: id={}, {}", id, entity);
        
        // TODO update
        
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") Long id) {
        
        log.info("delete: id={}", id);
        
        // TODO delete
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class PersonDTO {
        
        private String name;
        private int number;
    }
}
