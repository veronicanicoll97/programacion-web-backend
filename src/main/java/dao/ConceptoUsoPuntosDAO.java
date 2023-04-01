package dao;

import model.ConceptoUsoPuntos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ConceptoUsoPuntosDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ConceptoUsoPuntosDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarConceptoUsoPuntos(ConceptoUsoPuntos pais) {
        entityManager.getTransaction().begin();
        entityManager.persist(pais);
        entityManager.getTransaction().commit();
    }

    public void actualizarConceptoUsoPuntos(ConceptoUsoPuntos pais) {
        entityManager.getTransaction().begin();
        entityManager.merge(pais);
        entityManager.getTransaction().commit();
    }

    public void eliminarConceptoUsoPuntos(int idConceptoUsoPuntos) {
        entityManager.getTransaction().begin();
        ConceptoUsoPuntos pais = entityManager.find(ConceptoUsoPuntos.class, idConceptoUsoPuntos);
        entityManager.remove(pais);
        entityManager.getTransaction().commit();
    }

    public List<ConceptoUsoPuntos> listarConceptoUsoPuntoses() {
        Query query = entityManager.createQuery("SELECT p FROM ConceptoUsoPuntos p");
        return query.getResultList();
    }

    public ConceptoUsoPuntos buscarConceptoUsoPuntosPorId(int idConceptoUsoPuntos) {
        return entityManager.find(ConceptoUsoPuntos.class, idConceptoUsoPuntos);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}