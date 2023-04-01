package com.tp.tpback;

import dao.ReglaAsignacionPuntoDAO;
import model.ReglaAsignacionPunto;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/reglaasingaciones")
public class ReglaAsignacionPuntoResource {

    @Inject
    ReglaAsignacionPuntoDAO reglaasingacionDAO;

    @Path("/listar")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<ReglaAsignacionPunto> listar() {
        return reglaasingacionDAO.listarReglaAsignacionPuntoes();
    }

    @Path("/actualizar-reglaasingacion")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response actualizar_reglaasingacion(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_reglaasingacion");
            int limit_inf = json.getInt("limit_inf");
            int limit_sup = json.getInt("limit_sup");
            int monto_eq_puntos = json.getInt("monto_eq_puntos");
            System.out.println("id: " + id);
            System.out.println("limit_inf: " + limit_inf);
            System.out.println("limit_sup: " + limit_sup);
            System.out.println("monto_eq_puntos: " + monto_eq_puntos);
            ReglaAsignacionPunto reglaasingacion = reglaasingacionDAO.buscarReglaAsignacionPuntoPorId(id);
            if(reglaasingacion != null){
                reglaasingacion.setLimiteInferior(limit_inf);
                reglaasingacion.setLimiteSuperior(limit_sup);
                reglaasingacion.setMontoEquivalenciaPunto(monto_eq_puntos);
                reglaasingacionDAO.actualizarReglaAsignacionPunto(reglaasingacion);
                Map<String, String> responseObj = getResponse("OK", "200", "Regla asignacion actualizado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un reglaasingacion con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al actualizar un reglaasingacion"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/get-equivalencia-monto-puntos")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response get_equivalencia(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int monto = json.getInt("monto");
            List<ReglaAsignacionPunto> reglaasingacion = reglaasingacionDAO.listarReglaAsignacionPuntoes();
            /*buscamos en que intervalo de limit_inf y limit_sup está nuestro monto y retornamos el monto_eq_puntos*/
            for (ReglaAsignacionPunto reglaAsignacionPunto : reglaasingacion) {
                if(monto >= reglaAsignacionPunto.getLimiteInferior() && monto <= reglaAsignacionPunto.getLimiteSuperior()){
                    int monto_eq_puntos = monto / reglaAsignacionPunto.getMontoEquivalenciaPunto();
                    Map<String, String> responseObj = getResponse("OK", "200", "Se encontró una regla de asignación, se devuelve los puntos equivalentes",  monto_eq_puntos+"");
                    responseObj.put("puntos_equivalentes", String.valueOf(monto_eq_puntos));
                    responseObj.put("intervalo", reglaAsignacionPunto.getLimiteInferior() + " - " + reglaAsignacionPunto.getLimiteSuperior());
                    responseObj.put("puntosxmonto", "1" + " puntos por " + reglaAsignacionPunto.getMontoEquivalenciaPunto() + " gs");
                    responseObj.put("id_regla_asignacion", reglaAsignacionPunto.getIdAsignacion()+"");
                    builder = Response.status(Response.Status.OK).entity(responseObj);
                    return builder.build();
                }
            }
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró una regla asingacin con con ese intervalo de monto", "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);

        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al encontrar una regla asingacion con con ese intervalo de monto"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/crear-reglaasingacion")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response crear_reglaasingacion(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int limit_inf = json.getInt("limit_inf");
            int limit_sup = json.getInt("limit_sup");
            int monto_eq_puntos = json.getInt("monto_eq_puntos");
            System.out.println("limit_inf: " + limit_inf);
            System.out.println("limit_sup: " + limit_sup);
            System.out.println("monto_eq_puntos: " + monto_eq_puntos);
            ReglaAsignacionPunto reglaasingacion = new ReglaAsignacionPunto();
            reglaasingacion.setLimiteInferior(limit_inf);
            reglaasingacion.setLimiteSuperior(limit_sup);
            reglaasingacion.setMontoEquivalenciaPunto(monto_eq_puntos);
            if(reglaasingacion != null){
                reglaasingacionDAO.insertarReglaAsignacionPunto(reglaasingacion);
                Map<String, String> responseObj = getResponse("OK", "200", "Regla asignacion creado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear un reglaasingacion", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear un reglaasingacion: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/eliminar-reglaasingacion")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response eliminar_reglaasingacion(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_reglaasingacion");
            System.out.println("id: " + id);
            ReglaAsignacionPunto reglaasingacion = reglaasingacionDAO.buscarReglaAsignacionPuntoPorId(id);
            if(reglaasingacion != null){
                reglaasingacionDAO.eliminarReglaAsignacionPunto(reglaasingacion.getIdAsignacion());
                Map<String, String> responseObj = getResponse("OK", "200", "Regla asignacion eliminado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un reglaasingacion con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al eliminar un reglaasingacion"+e.getMessage(), "");
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