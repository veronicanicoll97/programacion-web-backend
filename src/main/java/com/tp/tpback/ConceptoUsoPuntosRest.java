package com.tp.tpback;

import py.com.progweb.prueba.ejb.ConceptoUsoPuntosDAO;
import py.com.progweb.prueba.model.ConceptoUsoPuntos;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;

@Path("concepto_puntos")
@Consumes("application/json")
@Produces("application/json")
public class ConceptoUsoPuntosRest {

    @Inject
    private ConceptoUsoPuntosDAO puntosDAO;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(puntosDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(ConceptoUsoPuntos p) {
        try{
            this.puntosDAO.agregar(p);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle
                    if(t.getMessage().contains("concepto_puntos_puntos_requeridos_check"))
                        return Response.status(409).entity("puntos_requeridos value must be > 0").build();
                }

            }
        }
        return Response.ok().build();
    }


    @PUT
    @Path("/{id}")
    public Response update(ConceptoUsoPuntos c, @PathParam("id") int id) {
        try{
            this.puntosDAO.update(c, id);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it
                    if(t.getMessage().contains("concepto_puntos_puntos_requeridos_check"))
                        return Response.status(409).entity("puntos_requeridos value must be > 0").build();
                }

            }
        } catch (ParseException e) {
            e.getMessage();
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        this.puntosDAO.delete(id);
        return Response.ok().build();
    }

}
