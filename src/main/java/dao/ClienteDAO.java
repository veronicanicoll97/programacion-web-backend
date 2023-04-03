package dao;

import jdk.nashorn.internal.runtime.Context;
import model.Cliente;

import javax.persistence.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class ClienteDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ClienteDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
    }

    public void actualizarCliente(Cliente cliente) {
        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();
    }

    public void eliminarCliente(int idCliente) {
        entityManager.getTransaction().begin();
        Cliente cliente = entityManager.find(Cliente.class, idCliente);
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
    }

    public List<Cliente> listarClientes() {
        Query query = entityManager.createQuery("SELECT p FROM Cliente p");
        return query.getResultList();
    }


    public List listarClienteByName(String nombre) {
        System.out.print("NOMBREEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        System.out.print("Nombre de cliente: " + nombre);
        Query query = entityManager.createQuery(
                "SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre"
        );

        query.setParameter("nombre" , "%" + nombre + "%");

        System.out.println(query);
        return query.getResultList();
    }

    public List listarClienteByApellido(String apellido) {
        System.out.print("Apellido de cliente: " + apellido);
        Query query = entityManager.createQuery(
                "SELECT c FROM Cliente c WHERE c.apellido LIKE :apellido"
        );

        query.setParameter("apellido", "%" + apellido + "%");

        System.out.println(query);
        return query.getResultList();
    }

    public List listarClienteByFecha(String fecha) {
        System.out.print("Apellido de cliente: " + fecha);
        java.util.Date fechaNacimiento = null;
        try{
            fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        }
        catch (Exception e) {
            System.out.print("Error en el formateo de fecha: " + e);
        }

        Query query = entityManager.createQuery(
                "SELECT c FROM Cliente c WHERE c.fechaNacimiento = :fechaNacimiento"
        );

        query.setParameter("fechaNacimiento", fechaNacimiento);

        System.out.println(query);
        return query.getResultList();
    }

    public Cliente buscarClientePorId(int idCliente) {
        return entityManager.find(Cliente.class, idCliente);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}