package com.tp.tpback;

import py.com.progweb.prueba.ejb.UsoPuntosDAO;
import py.com.progweb.prueba.model.UsoPuntos;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("uso_puntos")
@Consumes("application/json")
@Produces("application/json")
public class UsoPuntosRest {

    @Inject
    private UsoPuntosDAO usoPuntosDAO;

    @POST
    @Path("/")
    public Response usarPuntos(UsoPuntos u) {
        try {
            this.usoPuntosDAO.agregar(u);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it

                    if(t.getMessage().contains("cabecera_id_concepto_fk")) {
                        return Response.status(409).entity("No existe un concepto registrado con ese id").build();

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
        }catch (Throwable e){

            return Response.status(409).entity(e.getMessage()).build();

        }
        return Response.ok().build();
    }

}
