package dao;

import model.Cabecera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class CabeceraDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public CabeceraDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarCabecera(Cabecera cabecera) {
        entityManager.getTransaction().begin();
        entityManager.persist(cabecera);
        entityManager.getTransaction().commit();
    }

    public void actualizarCabecera(Cabecera cabecera) {
        entityManager.getTransaction().begin();
        entityManager.merge(cabecera);
        entityManager.getTransaction().commit();
    }

    public void eliminarCabecera(int idCabecera) {
        entityManager.getTransaction().begin();
        Cabecera cabecera = entityManager.find(Cabecera.class, idCabecera);
        entityManager.remove(cabecera);
        entityManager.getTransaction().commit();
    }

    public List<Cabecera> listarCabeceraes() {
        Query query = entityManager.createQuery("SELECT p FROM Cabecera p");
        return query.getResultList();
    }

    public Cabecera buscarCabeceraPorId(int idCabecera) {
        return entityManager.find(Cabecera.class, idCabecera);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}