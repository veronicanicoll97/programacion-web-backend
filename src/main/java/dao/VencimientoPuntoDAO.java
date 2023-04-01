package dao;

import model.VencimientoPunto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class VencimientoPuntoDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public VencimientoPuntoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarVencimientoPunto(VencimientoPunto vencimientoPunto) {
        entityManager.getTransaction().begin();
        entityManager.persist(vencimientoPunto);
        entityManager.getTransaction().commit();
    }

    public void actualizarVencimientoPunto(VencimientoPunto vencimientoPunto) {
        entityManager.getTransaction().begin();
        entityManager.merge(vencimientoPunto);
        entityManager.getTransaction().commit();
    }

    public void eliminarVencimientoPunto(int idVencimientoPunto) {
        entityManager.getTransaction().begin();
        VencimientoPunto vencimientoPunto = entityManager.find(VencimientoPunto.class, idVencimientoPunto);
        entityManager.remove(vencimientoPunto);
        entityManager.getTransaction().commit();
    }

    public List<VencimientoPunto> listarVencimientoPuntoes() {
        Query query = entityManager.createQuery("SELECT p FROM VencimientoPunto p");
        return query.getResultList();
    }

    public List<VencimientoPunto> listarVencimientoPuntoesValidos() {
        Query query = entityManager.createQuery("SELECT p FROM VencimientoPunto p where p.fechaInicio <= CURRENT_DATE and p.fechaFin >= CURRENT_DATE");
        return query.getResultList();
    }

    public VencimientoPunto buscarVencimientoPuntoPorId(int idVencimientoPunto) {
        return entityManager.find(VencimientoPunto.class, idVencimientoPunto);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}