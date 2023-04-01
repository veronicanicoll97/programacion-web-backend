package com.tp.tpback;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("persona")
@Consumes("application/json")
@Produces("application/json")
public class PersonaRest {
	
	@Inject
	private Persona persona;
	
	@GET
	@Path("/")
	public Response listar() {
		return Response.ok(persona.lista()).build();
	}
	
	@POST
	@Path("/")
	public Response crear(Persona p) {
		this.persona.agregar(p);
		return Response.ok().build();
	}

}
