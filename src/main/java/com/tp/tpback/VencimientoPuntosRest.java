package com.tp.tpback;



import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;

@Path("vencimiento_puntos")
@Consumes("application/json")
@Produces("application/json")
public class VencimientoPuntosRest {

    @Inject
    private VencimientoPuntos vencimientoPuntos;
    private Object vencimientoPuntos;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(vencimientoPuntos.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(VencimientoPuntos p) {
        try{
            this.vencimientoPuntos.agregar(p);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException | ParseException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle
                    if(t.getMessage().contains("vencimiento_puntos_check"))
                        return Response.status(409).entity("fechaFin no puede ser menor a fechaInicio").build();
                }

            }
        }catch (Throwable e){
            return Response.status(409).entity(e.getMessage()).build();
        }
        return Response.ok().build();
    }


    @PUT
    @Path("/{id}")
    public Response update(VencimientoPuntos c, @PathParam("id") int id) {
        try{
            this.vencimientoPuntos.update(c, id);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it
                    if(t.getMessage().contains("vencimiento_puntos_check"))
                        return Response.status(409).entity("fechaFin no puede ser menor a fechaInicio").build();
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
        this.vencimientoPuntos.delete(id);
        return Response.ok().build();
    }


}
