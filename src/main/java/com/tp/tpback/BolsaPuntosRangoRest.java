package com.tp.tpback;

import dao.BolsaPuntosDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("bolsapuntosporcliente")
@Consumes("application/json")
@Produces("application/json")

public class BolsaPuntosRangoRest {
    
    @Inject
    private BolsaPuntosDAO bolsapuntos;

    @GET
    // persona/
    @Path("/{idCliente}")
    public Response listarRango(@PathParam("idCliente") Integer idCliente){
        return Response.ok(bolsapuntos.listaCliente(idCliente)).build();
    }

}
