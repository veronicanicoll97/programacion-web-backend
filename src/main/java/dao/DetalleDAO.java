package dao;

import model.Detalle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DetalleDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public DetalleDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarDetalle(Detalle detalle) {
        entityManager.getTransaction().begin();
        entityManager.persist(detalle);
        entityManager.getTransaction().commit();
    }

    public void actualizarDetalle(Detalle detalle) {
        entityManager.getTransaction().begin();
        entityManager.merge(detalle);
        entityManager.getTransaction().commit();
    }

    public void eliminarDetalle(int idDetalle) {
        entityManager.getTransaction().begin();
        Detalle detalle = entityManager.find(Detalle.class, idDetalle);
        entityManager.remove(detalle);
        entityManager.getTransaction().commit();
    }

    public List<Detalle> listarDetalles() {
        Query query = entityManager.createQuery("SELECT p FROM Detalle p");
        return query.getResultList();
    }

    public Detalle buscarDetallePorId(int idDetalle) {
        return entityManager.find(Detalle.class, idDetalle);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}