package dao;

import model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

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


    public List listarClienteByName(String nombreCliente) {
        String clienteByName = "select * from clientes where nombre like '%" + nombreCliente + "%'";
        Query query = entityManager.createQuery(clienteByName);
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