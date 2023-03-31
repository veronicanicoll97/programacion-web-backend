package com.tp.tpback;

import py.com.progweb.prueba.ejb.BolsaPuntosDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("bolsapuntosporcliente")
@Consumes("application/json")
@Produces("application/json")

public class BolsaPuntosRangoRest {
    
    @Inject
    private BolsaPuntosDAO bolsapuntosDAO;

    @GET
    // persona/
    @Path("/{idCliente}")
    public Response listarRango(@PathParam("idCliente") Integer idCliente){
        return Response.ok(bolsapuntosDAO.listaCliente(idCliente)).build();
    }

}
