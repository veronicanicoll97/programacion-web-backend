package dao;

import model.Pais;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class PaisDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public PaisDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarPais(Pais pais) {
        entityManager.getTransaction().begin();
        entityManager.persist(pais);
        entityManager.getTransaction().commit();
    }

    public void actualizarPais(Pais pais) {
        entityManager.getTransaction().begin();
        entityManager.merge(pais);
        entityManager.getTransaction().commit();
    }

    public void eliminarPais(int idPais) {
        entityManager.getTransaction().begin();
        Pais pais = entityManager.find(Pais.class, idPais);
        entityManager.remove(pais);
        entityManager.getTransaction().commit();
    }

    public List<Pais> listarPaises() {
        Query query = entityManager.createQuery("SELECT p FROM Pais p");
        return query.getResultList();
    }

    public Pais buscarPaisPorId(int idPais) {
        return entityManager.find(Pais.class, idPais);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}