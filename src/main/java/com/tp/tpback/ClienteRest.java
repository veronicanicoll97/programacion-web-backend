package com.tp.tpback;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.Cliente;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
package com.tp.tpback;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
public class ClienteRest {

    @Inject
    private Cliente persona;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(persona.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(Cliente p) {
        try{
            this.persona.agregar(p);
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it

                    if(t.getMessage().contains("cliente_nro_documento_key"))
                        return Response.status(409).entity("nroDocumento value already exists ").build();
                    if(t.getMessage().contains("cliente_nro_documento_check"))
                        return Response.status(409).entity("nro_documento value must be > 0").build();
                    if(t.getMessage().contains("cliente_email_key"))
                        return Response.status(409).entity("email value already exists ").build();
                }

            }
        }
        return Response.ok().build();
    }


    @PUT
    @Path("/{id}")
    public Response update(Cliente c, @PathParam("id") String id) {
        try{
            this.persona.update(c, Integer.parseInt(id));
            return Response.ok().build();
        }catch (EJBTransactionRolledbackException e){
            Throwable t = e.getCause();
            while ((t != null) ) {
                t = t.getCause();
                if (t instanceof SQLException) {
                    // Here you're sure you have a ConstraintViolationException, you can handle it

                    if(t.getMessage().contains("cliente_nro_documento_key"))
                        return Response.status(409).entity("nroDocumento value already exists ").build();
                    if(t.getMessage().contains("cliente_nro_documento_check"))
                        return Response.status(409).entity("nro_documento value must be > 0").build();
                    if(t.getMessage().contains("cliente_email_key"))
                        return Response.status(409).entity("email value already exists ").build();
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
        this.persona.delete(id);
        return Response.ok().build();
    }

}
