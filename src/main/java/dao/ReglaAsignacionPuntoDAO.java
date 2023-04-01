package dao;

import model.ReglaAsignacionPunto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ReglaAsignacionPuntoDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ReglaAsignacionPuntoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarReglaAsignacionPunto(ReglaAsignacionPunto regla_asignacion) {
        entityManager.getTransaction().begin();
        entityManager.persist(regla_asignacion);
        entityManager.getTransaction().commit();
    }

    public void actualizarReglaAsignacionPunto(ReglaAsignacionPunto regla_asignacion) {
        entityManager.getTransaction().begin();
        entityManager.merge(regla_asignacion);
        entityManager.getTransaction().commit();
    }

    public void eliminarReglaAsignacionPunto(int idReglaAsignacionPunto) {
        entityManager.getTransaction().begin();
        ReglaAsignacionPunto regla_asignacion = entityManager.find(ReglaAsignacionPunto.class, idReglaAsignacionPunto);
        entityManager.remove(regla_asignacion);
        entityManager.getTransaction().commit();
    }

    public List<ReglaAsignacionPunto> listarReglaAsignacionPuntoes() {
        Query query = entityManager.createQuery("SELECT p FROM ReglaAsignacionPunto p");
        return query.getResultList();
    }

    public ReglaAsignacionPunto buscarReglaAsignacionPuntoPorId(int idReglaAsignacionPunto) {
        return entityManager.find(ReglaAsignacionPunto.class, idReglaAsignacionPunto);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}