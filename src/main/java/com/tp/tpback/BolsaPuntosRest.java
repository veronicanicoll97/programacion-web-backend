package com.tp.tpback;



import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;


@Path("bolsapuntos")
@Consumes("application/json")
@Produces("application/json")
public class BolsaPuntosRest {

    @Inject
    private BolsaPuntos bolsapuntos;

    @GET
    // persona/
    @Path("/")
    public Response listar(){
        return Response.ok(bolsapuntos.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(BolsaPuntos bp){
        try {
            this.bolsapuntos.agregar(bp);
            return Response.ok().build();
        } catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it

                    if(t.getMessage().contains("bolsa_puntos_fk")) {
                        return Response.status(409).entity("No existe un cliente con ese id").build();

                    } else if(t.getMessage().contains("bolsa_puntos_check")) {
                        return Response.status(409).entity("fecha de caducidad debe ser mayor a fecha de asignacion").build();
                    } else if(t.getMessage().contains("bolsa_puntos_puntos_totales_check")) {
                        return Response.status(409).entity("puntos totales debe ser positivo mayor a cero").build();
                    } else if(t.getMessage().contains("bolsa_puntos_puntos_utilizados_check")) {
                        return Response.status(409).entity("puntos utilizados debe ser no negativo").build();
                    } else if(t.getMessage().contains("bolsa_puntos_saldo_puntos_check")) {
                        return Response.status(409).entity("saldo puntos debe ser no negativo").build();
                    } else if(t.getMessage().contains("bolsa_puntos_monto_operacion_check")) {
                        return Response.status(500).entity("monto operacion debe ser mayor a cero").build();

                    } else if(t.getMessage().contains("fecha_asignacion")) {
                        return Response.status(409).entity("el campo fecha_asignacion no puede ser nulo").build();
                    }else if(t.getMessage().contains("fecha_caducidad")) {
                        return Response.status(409).entity("el campo fecha_caducidad no puede ser nulo").build();
                    } else {
                        return Response.status(409).entity("error no identificado").build();
                    }
                }

            }
        }
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(BolsaPuntos bp, @PathParam("id") String id) {
        try{
            this.bolsapuntos.update(bp, Integer.parseInt(id));
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it

                    if(t.getMessage().contains("bolsa_puntos_fk")) {
                        return Response.status(409).entity("No existe un cliente con ese id").build();

                    } else if(t.getMessage().contains("bolsa_puntos_check")) {
                        return Response.status(409).entity("fecha de caducidad debe ser mayor a fecha de asignacion").build();
                    } else if(t.getMessage().contains("bolsa_puntos_puntos_totales_check")) {
                        return Response.status(409).entity("puntos totales debe ser positivo mayor a cero").build();
                    } else if(t.getMessage().contains("bolsa_puntos_puntos_utilizados_check")) {
                        return Response.status(409).entity("puntos utilizados debe ser no negativo").build();
                    } else if(t.getMessage().contains("bolsa_puntos_saldo_puntos_check")) {
                        return Response.status(409).entity("saldo puntos debe ser no negativo").build();
                    } else if(t.getMessage().contains("bolsa_puntos_monto_operacion_check")) {
                        return Response.status(500).entity("monto operacion debe ser mayor a cero").build();

                    } else if(t.getMessage().contains("fecha_asignacion")) {
                        return Response.status(409).entity("el campo fecha_asignacion no puede ser nulo").build();
                    }else if(t.getMessage().contains("fecha_caducidad")) {
                        return Response.status(409).entity("el campo fecha_caducidad no puede ser nulo").build();
                    } else {
                        return Response.status(409).entity("error no identificado").build();
                    }
                }

            }
        }

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        this.bolsapuntos.delete(id);
        return Response.ok().build();
    }

}