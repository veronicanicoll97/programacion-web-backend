package com.tp.tpback;

import dao.PaisDAO;
import model.Pais;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/paises")
public class PaisResource {

    @Inject
    PaisDAO paisDAO;

    @Path("/listar")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Pais> listar() {
        return paisDAO.listarPaises();
    }

    @Path("/actualizar-pais")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response actualizar_pais(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_pais");
            String descripcion = json.getString("descripcion");
            String nacionalidad = json.getString("nacionalidad");
            System.out.println("id: " + id);
            System.out.println("descripcion: " + descripcion);
            Pais pais = paisDAO.buscarPaisPorId(id);
            if(pais != null){
                pais.setDescripcion(descripcion);
                pais.setNacionalidad(nacionalidad);
                paisDAO.actualizarPais(pais);
                Map<String, String> responseObj = getResponse("OK", "200", "País actualizado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un país con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al actualizar el país"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/crear-pais")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response crear_pais(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            String descripcion = json.getString("descripcion");
            String nacionalidad = json.getString("nacionalidad");
            System.out.println("descripcion: " + descripcion);
            Pais pais = new Pais();
            pais.setDescripcion(descripcion);
            pais.setNacionalidad(nacionalidad);
            if(pais != null){
                paisDAO.insertarPais(pais);
                Map<String, String> responseObj = getResponse("OK", "200", "País creado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el país", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el país: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/eliminar-pais")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response eliminar_pais(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_pais");
            System.out.println("id: " + id);
            Pais pais = paisDAO.buscarPaisPorId(id);
            if(pais != null){
                paisDAO.eliminarPais(pais.getIdPais());
                Map<String, String> responseObj = getResponse("OK", "200", "País eliminado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un país con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al eliminar el país"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }
        return builder.build();
    }


    Map<String, String> getResponse(String status, String code, String msg, String data){
        Map<String, String> responseObj = new HashMap<>();
        responseObj.put("status", status);
        responseObj.put("code", code);
        responseObj.put("msg", msg);
        responseObj.put("data", data);
        return responseObj;
    }
}