package com.tp.tpback;

import py.com.progweb.prueba.ejb.PersonaDAO;
import py.com.progweb.prueba.model.Persona;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("persona")
@Consumes("application/json")
@Produces("application/json")
public class PersonaRest {
	
	@Inject
	private PersonaDAO personaDAO;
	
	@GET
	@Path("/")
	public Response listar() {
		return Response.ok(personaDAO.lista()).build();
	}
	
	@POST
	@Path("/")
	public Response crear(Persona p) {
		this.personaDAO.agregar(p);
		return Response.ok().build();
	}

}
