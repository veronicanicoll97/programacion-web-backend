package com.tp.tpback;

import py.com.progweb.prueba.ejb.ReglaAsignacionDAO;
import py.com.progweb.prueba.model.ReglaAsignacionPunto;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("regla_asignacion")
@Consumes("application/json")
@Produces("application/json")
public class ReglaAsignacionRest {

    @Inject
    private ReglaAsignacionDAO reglaAsigacionDAO;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(reglaAsigacionDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(ReglaAsignacionPunto p) {

        try{
            this.reglaAsigacionDAO.agregar(p);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle
                    if(t.getMessage().contains("reglas_asig_puntos_check"))
                        return Response.status(409).entity("monto no puede ser <= 0").build();
                    if(t.getMessage().contains("reglas_asig_check"))
                        return Response.status(409).entity("el limite superior no puede ser menor al limite inferior").build();
                    if(t.getMessage().contains("reglas_asig_limite_inferior_check"))
                        return Response.status(409).entity("el limite inferior debe ser > 0").build();
                }

            }
        }catch (Throwable e){
            return Response.status(409).entity(e.getMessage()).build();
        }
        return Response.ok().build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        this.reglaAsigacionDAO.delete(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(ReglaAsignacionPunto c, @PathParam("id") int id) {
        try{
            this.reglaAsigacionDAO.update(c, id);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle
                    if(t.getMessage().contains("reglas_asig_puntos_check"))
                        return Response.status(409).entity("monto no puede ser <= 0").build();
                    if(t.getMessage().contains("reglas_asig_check"))
                        return Response.status(409).entity("el limite superior no puede ser menor al limite inferior").build();
                    if(t.getMessage().contains("reglas_asig_limite_inferior_check"))
                        return Response.status(409).entity("el limite inferior debe ser > 0").build();
                }

            }
        }catch (Throwable e){
            return Response.status(409).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }

}
