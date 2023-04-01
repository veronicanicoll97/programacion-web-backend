package com.tp.tpback;

import dao.ConceptoUsoPuntosDAO;
import model.ConceptoUsoPuntos;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/conceptos")
public class ConceptoUsoResource {

    @Inject
    ConceptoUsoPuntosDAO conceptoDAO;

    @Path("/listar")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<ConceptoUsoPuntos> listar() {
        return conceptoDAO.listarConceptoUsoPuntoses();
    }

    @Path("/actualizar-concepto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response actualizar_concepto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_concepto");
            String descripcion = json.getString("descripcion");
            int puntos_requeridos = json.getInt("puntos_requeridos");
            System.out.println("id: " + id);
            System.out.println("descripcion: " + descripcion);
            System.out.println("puntos_requeridos: " + puntos_requeridos);
            ConceptoUsoPuntos concepto = conceptoDAO.buscarConceptoUsoPuntosPorId(id);
            if(concepto != null){
                concepto.setDescripcion(descripcion);
                concepto.setPuntosRequeridos(puntos_requeridos);
                conceptoDAO.actualizarConceptoUsoPuntos(concepto);
                Map<String, String> responseObj = getResponse("OK", "200", "Concepto actualizado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un concepto con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al actualizar un concepto"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/crear-concepto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response crear_concepto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            String descripcion = json.getString("descripcion");
            int puntos_requeridos = json.getInt("puntos_requeridos");
            System.out.println("descripcion: " + descripcion);
            ConceptoUsoPuntos concepto = new ConceptoUsoPuntos();
            concepto.setDescripcion(descripcion);
            concepto.setPuntosRequeridos(puntos_requeridos);
            if(concepto != null){
                conceptoDAO.insertarConceptoUsoPuntos(concepto);
                Map<String, String> responseObj = getResponse("OK", "200", "Concepto creado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear un concepto", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear un concepto: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/eliminar-concepto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response eliminar_concepto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_concepto");
            System.out.println("id: " + id);
            ConceptoUsoPuntos concepto = conceptoDAO.buscarConceptoUsoPuntosPorId(id);
            if(concepto != null){
                conceptoDAO.eliminarConceptoUsoPuntos(concepto.getIdConcepto());
                Map<String, String> responseObj = getResponse("OK", "200", "Concepto eliminado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un concepto con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al eliminar un concepto"+e.getMessage(), "");
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