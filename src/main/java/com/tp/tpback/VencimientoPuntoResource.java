package com.tp.tpback;

import dao.VencimientoPuntoDAO;
import model.VencimientoPunto;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/vencimientoPuntos")
public class VencimientoPuntoResource {

    @Inject
    VencimientoPuntoDAO vencimientoPuntoDAO;


    @Path("/listar")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<VencimientoPunto> listar() {
        return vencimientoPuntoDAO.listarVencimientoPuntoes();
    }

    @Path("/actualizar-vencimientoPunto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response actualizar_vencimientoPunto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_vencimientoPunto");
            String fecha_inicio = json.getString("fecha_inicio");
            String fecha_fin = json.getString("fecha_fin");
            int dias_duracion = json.getInt("dias_duracion");
            /*convert a text DD/MM/YYYY to a explicit java.sql.Date */
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date_inicio = formatter.parse(fecha_inicio);
            Date date_fin = formatter.parse(fecha_fin);
            /*convert java.util.date to java.sql.date*/
            java.sql.Date sqlDate_inicio = new java.sql.Date(date_inicio.getTime());
            java.sql.Date sqlDate_fin = new java.sql.Date(date_fin.getTime());
            VencimientoPunto vencimientoPunto = vencimientoPuntoDAO.buscarVencimientoPuntoPorId(id);
            if(vencimientoPunto != null){
                vencimientoPunto.setFechaInicio(sqlDate_inicio);
                vencimientoPunto.setFechaFin(sqlDate_fin);
                vencimientoPunto.setDiasDuracion(dias_duracion);
                vencimientoPuntoDAO.actualizarVencimientoPunto(vencimientoPunto);
                Map<String, String> responseObj = getResponse("OK", "200", "VencimientoPunto actualizado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un vencimientoPunto con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al actualizar el vencimientoPunto"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/crear-vencimientoPunto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response crear_vencimientoPunto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            String fecha_inicio = json.getString("fecha_inicio");
            String fecha_fin = json.getString("fecha_fin");
            int dias_duracion = json.getInt("dias_duracion");
            /*convert a text DD/MM/YYYY to a explicit java.sql.Date */
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date_inicio = formatter.parse(fecha_inicio);
            Date date_fin = formatter.parse(fecha_fin);
            /*convert java.util.date to java.sql.date*/
            java.sql.Date sqlDate_inicio = new java.sql.Date(date_inicio.getTime());
            java.sql.Date sqlDate_fin = new java.sql.Date(date_fin.getTime());

            VencimientoPunto vencimientoPunto = new VencimientoPunto();
            vencimientoPunto.setFechaInicio(sqlDate_inicio);
            vencimientoPunto.setFechaFin(sqlDate_fin);
            vencimientoPunto.setDiasDuracion(dias_duracion);
            if(vencimientoPunto != null){
                vencimientoPuntoDAO.insertarVencimientoPunto(vencimientoPunto);
                Map<String, String> responseObj = getResponse("OK", "200", "VencimientoPunto creado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el vencimientoPunto", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el vencimientoPunto: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/eliminar-vencimientoPunto")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response eliminar_vencimientoPunto(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_vencimientoPunto");
            System.out.println("id: " + id);
            VencimientoPunto vencimientoPunto = vencimientoPuntoDAO.buscarVencimientoPuntoPorId(id);
            if(vencimientoPunto != null){
                vencimientoPuntoDAO.eliminarVencimientoPunto(vencimientoPunto.getIdVencimiento());
                Map<String, String> responseObj = getResponse("OK", "200", "VencimientoPunto eliminado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un vencimientoPunto con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al eliminar el vencimientoPunto"+e.getMessage(), "");
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