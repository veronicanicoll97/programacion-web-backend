package com.tp.tpback;

import dao.BolsaPuntoDAO;
import dao.ClienteDAO;
import dao.PaisDAO;
import model.Cliente;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

@Path("/clientes")
public class ClienteResource {

    @Inject
    ClienteDAO clienteDAO;
    @Inject
    BolsaPuntoDAO bolsaDAO;

    @Path("/listar")
    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> listar() {
        System.out.print("HOLIIIIII");
        return clienteDAO.listarClientes();
    }

    @Path("/actualizar-cliente")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response actualizar_cliente(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_cliente");
            String nombre = json.getString("nombre");
            String apellido = json.getString("apellido");
            String telefono = json.getString("telefono");
            String email = json.getString("email");
            String nro_documento = json.getString("nro_documento");
            int id_tipo_documento = json.getInt("id_tipo_documento");
            int id_pais = json.getInt("id_pais");
            String fecha_nacimiento = json.getString("fecha_nacimiento");
            System.out.println("id: " + id);
            System.out.println("nombre: " + nombre);
            System.out.println("apellido: " + apellido);
            System.out.println("telefono: " + telefono);
            System.out.println("email: " + email);
            System.out.println("nro_documento: " + nro_documento);
            System.out.println("id_tipo_documento: " + id_tipo_documento);
            System.out.println("id_pais: " + id_pais);
            System.out.println("fecha_nacimiento: " + fecha_nacimiento);
            /*convert a text DD/MM/YYYY to a explicit java.sql.Date */
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(fecha_nacimiento);
            /*convert java.util.date to java.sql.date*/
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Cliente cliente = clienteDAO.buscarClientePorId(id);
            if(cliente != null){
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setTelefono(telefono);
                cliente.setEmail(email);
                cliente.setNroDocumento(nro_documento);
                cliente.setIdTipoDocumento(id_tipo_documento);
                cliente.setIdPais(id_pais);
                cliente.setFechaNacimiento(sqlDate);
                clienteDAO.actualizarCliente(cliente);
                Map<String, String> responseObj = getResponse("OK", "200", "Cliente actualizado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un cliente con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al actualizar el cliente"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }

        return builder.build();
    }

    @Path("/crear-cliente")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response crear_cliente(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            String nombre = json.getString("nombre");
            String apellido = json.getString("apellido");
            String telefono = json.getString("telefono");
            String email = json.getString("email");
            String nro_documento = json.getString("nro_documento");
            int id_tipo_documento = json.getInt("id_tipo_documento");
            int id_pais = json.getInt("id_pais");
            String fecha_nacimiento = json.getString("fecha_nacimiento");
            System.out.println("nombre: " + nombre);
            System.out.println("apellido: " + apellido);
            System.out.println("telefono: " + telefono);
            System.out.println("email: " + email);
            System.out.println("nro_documento: " + nro_documento);
            System.out.println("id_tipo_documento: " + id_tipo_documento);
            System.out.println("id_pais: " + id_pais);
            System.out.println("fecha_nacimiento: " + fecha_nacimiento);
            /*convert a text DD/MM/YYYY to a explicit java.sql.Date */
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(fecha_nacimiento);
            /*convert java.util.date to java.sql.date*/
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
            cliente.setNroDocumento(nro_documento);
            cliente.setIdTipoDocumento(id_tipo_documento);
            cliente.setIdPais(id_pais);
            cliente.setFechaNacimiento(sqlDate);
            if(cliente != null){
                clienteDAO.insertarCliente(cliente);
                Map<String, String> responseObj = getResponse("OK", "200", "Cliente creado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el cliente", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e) {
            Map<String, String> responseObj = getResponse("ERROR", "500", "No se pudo crear el cliente: "+e.getMessage(), "");
            builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/eliminar-cliente")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public Response eliminar_cliente(JsonObject json) {
        Response.ResponseBuilder builder = null;
        /*print to wildfly*/
        try{
            int id = json.getInt("id_cliente");
            System.out.println("id: " + id);
            Cliente cliente = clienteDAO.buscarClientePorId(id);
            if(cliente != null){
                clienteDAO.eliminarCliente(cliente.getIdCliente());
                Map<String, String> responseObj = getResponse("OK", "200", "Cliente eliminado exitosamente", "");
                builder = Response.status(Response.Status.OK).entity(responseObj);
            }else{
                Map<String, String> responseObj = getResponse("ERROR", "500", "No se encontró un cliente con ese id", "");
                builder = Response.status(Response.Status.NOT_FOUND).entity(responseObj);
            }
        }catch (Exception e){
            Map<String, String> responseObj = getResponse("ERROR", "500", "Error al eliminar el cliente"+e.getMessage(), "");
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObj);
        }
        return builder.build();
    }

    @Path("/cliente-by-name")
    @GET
    @Consumes( { MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> cliente_by_name(JsonObject json) {
        String nombre = json.getString("nombre");
        System.out.println("nombre: " + nombre);

        return  clienteDAO.listarClienteByName(nombre);
    }


    @Path("/cliente-by-apellido")
    @GET
    @Consumes( { MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> cliente_by_apellido(JsonObject json) {
        String apellido = json.getString("apellido");
        System.out.println("nombre: " + apellido);

        return  clienteDAO.listarClienteByApellido(apellido);
    }

    @Path("/cliente-by-fecha-cumple")
    @GET
    @Consumes( { MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> cliente_by_fecha_cumple(JsonObject json) {
        String fecha = json.getString("fecha");
        System.out.println("fecha: " + fecha);

        return  clienteDAO.listarClienteByFecha(fecha);
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