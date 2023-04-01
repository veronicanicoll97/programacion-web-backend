package com.tp.tpback;

import dao.*;
import model.*;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/usopuntos")
public class UsoPuntosResource {

    @Inject
    ClienteDAO clienteDAO;
    @Inject
    ConceptoUsoPuntosDAO conceptoUsoPuntosDAO;

    @Inject
    BolsaPuntoDAO bolsaDAO;

    @Inject
    CabeceraDAO cabeceraDAO;

    @Inject
    DetalleDAO detalleDAO;

    @Inject
    ReglaAsignacionPuntoDAO reglaasingacionDAO;

    @Inject
    VencimientoPuntoDAO vencimientoDAO;

    @Path("/agregar-puntos")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response get_equivalencia(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int monto = json.getInt("monto");
            int cliente_id = json.getInt("id_cliente");
            Cliente cliente = clienteDAO.buscarClientePorId(cliente_id);
            if(cliente == null){
                throw new Exception("No se encontró un cliente con ese id");
            }
            ReglaAsignacionPunto regla = null;

            List<ReglaAsignacionPunto> reglaasingacion = reglaasingacionDAO.listarReglaAsignacionPuntoes();
            /*buscamos en que intervalo de limit_inf y limit_sup está nuestro monto y retornamos el monto_eq_puntos*/
            for (ReglaAsignacionPunto reglaAsignacionPunto : reglaasingacion) {
                if(monto >= reglaAsignacionPunto.getLimiteInferior() && monto <= reglaAsignacionPunto.getLimiteSuperior()){
                    regla = reglaAsignacionPunto;
                }
            }

            if(regla == null){
                throw new Exception("No se encontró una regla asingacin con con ese intervalo de monto");
            }

            List<VencimientoPunto> vencimientos = vencimientoDAO.listarVencimientoPuntoesValidos();
            if(vencimientos == null){
                throw new Exception("No se encontró una parametrización de vencimiento de puntos");
            }
            if(vencimientos.size() == 0){
                throw new Exception("No se encontró una parametrización de vencimiento de puntos");
            }
            VencimientoPunto venc = vencimientos.get(0);

            int puntos = monto / regla.getMontoEquivalenciaPunto();

            BolsaPunto bolsa = new BolsaPunto();
            bolsa.setIdCliente(cliente.getIdCliente());
            bolsa.setSaldoPuntos(puntos);
            bolsa.setFechaAsignacionPunto(new java.sql.Date(new Date().getTime()));
            int days_to_add = venc.getDiasDuracion();
            /*add X days to java.sql.Date*/
            Calendar c = Calendar.getInstance();
            c.setTime(bolsa.getFechaAsignacionPunto());
            c.add(Calendar.DATE, days_to_add);
            bolsa.setFechaCaducidadPunto(new java.sql.Date(c.getTime().getTime()));
            bolsa.setMontoOperacion(monto);
            bolsa.setPuntajeAsignado(puntos);
            bolsa.setPuntajeUtilizado(0);
            bolsa.setSaldoPuntos(puntos);
            bolsaDAO.insertarBolsaPunto(bolsa);

            Map<String, String> responseObj = getResponse("OK", "200", "Bolsa de puntos creado exitosamente", "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al crear la bolsa y asignar los puntos al cliente: "+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/usarpuntos")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response usar_puntos(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id_cliente = json.getInt("id_cliente");
            int id_concepto_uso_puntos = json.getInt("id_concepto_uso_puntos");

            bolsaDAO.cerrarEntityManager();
            bolsaDAO.abrirEntity();

            Cliente cliente = clienteDAO.buscarClientePorId(id_cliente);
            ConceptoUsoPuntos concepto = conceptoUsoPuntosDAO.buscarConceptoUsoPuntosPorId(id_concepto_uso_puntos);

            if (cliente == null){
                throw new Exception ("No existe el cliente");
            }
            if (concepto == null){
                throw new Exception ("No existe el concepto");
            }

            List<BolsaPunto> bolsasCliente = bolsaDAO.listarBolsaPuntoByIdCliente(cliente.getIdCliente());

            if (bolsasCliente == null){
                throw new Exception ("No existen bolsas de puntos para el cliente");
            }
            if(bolsasCliente.size() == 0){
                throw new Exception ("No existen bolsas de puntos para el cliente");
            }

            /*sumar la cantidad de puntos saldos disponibles*/
            int puntosDisponibles = 0;
            for (BolsaPunto bolsa : bolsasCliente){
                System.out.println("BOLSA ID = " + bolsa.getIdBolsa()+ " SALDO = " + bolsa.getSaldoPuntos() + "");
                puntosDisponibles += bolsa.getSaldoPuntos();
            }

            if(puntosDisponibles < concepto.getPuntosRequeridos()){
                throw new Exception ("No hay suficientes puntos disponibles");
            }

            /*create an object cabecera and insert their details*/
            Cabecera cabecera = new Cabecera();
            cabecera.setIdCliente(cliente.getIdCliente());
            cabecera.setIdConcepto(concepto.getIdConcepto());
            cabecera.setPuntajeUtilizado(concepto.getPuntosRequeridos());
            Date fecha_uso = new Date();
            java.sql.Date fecha_uso_sql = new java.sql.Date(fecha_uso.getTime());
            cabecera.setFechaUso(fecha_uso_sql);
            cabeceraDAO.insertarCabecera(cabecera);
            if(cabecera.getIdCabecera() == 0){
                throw new Exception ("No se pudo crear la cabecera");
            }
            /*restar los puntos requeridos del concepto*/
            ArrayList<Detalle> detalles_insertados = new ArrayList<>();
            int puntosRestantes = concepto.getPuntosRequeridos();
            for (BolsaPunto bolsa : bolsasCliente){
                int puntos_usados = 0;
                if(puntosRestantes > 0){
                    if(bolsa.getSaldoPuntos() >= puntosRestantes){
                        bolsa.setSaldoPuntos(bolsa.getSaldoPuntos() - puntosRestantes);
                        puntos_usados = puntosRestantes;
                        puntosRestantes = 0;
                    }else{
                        puntos_usados = bolsa.getSaldoPuntos();
                        puntosRestantes -= bolsa.getSaldoPuntos();
                        bolsa.setSaldoPuntos(0);
                    }
                    bolsa.setPuntajeUtilizado(bolsa.getPuntajeUtilizado() + puntos_usados);
                    bolsaDAO.actualizarBolsaPunto(bolsa);
                    Detalle det = new Detalle(cabecera.getIdCabecera(),puntos_usados,bolsa.getIdBolsa());
                    detalleDAO.insertarDetalle(det);
                    if(det.getIdDetalle() == 0){
                        throw new Exception ("No se pudo crear el detalle");
                    }
                    detalles_insertados.add(det);
                }
            }

            EnviarCorreo.enviar_correo(cliente, cabecera, detalles_insertados);
            /*return response*/
            Map<String, String> responseObj = getResponse("OK", "200", "Se usaron los puntos correctamente", "");
            builder = Response.status(Response.Status.OK).entity(responseObj);
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudieron usar los puntos: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
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