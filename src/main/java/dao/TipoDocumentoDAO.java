package dao;

import model.TipoDocumento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TipoDocumentoDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public TipoDocumentoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarTipoDocumento(TipoDocumento tipo_doc) {
        entityManager.getTransaction().begin();
        entityManager.persist(tipo_doc);
        entityManager.getTransaction().commit();
    }

    public void actualizarTipoDocumento(TipoDocumento tipo_doc) {
        entityManager.getTransaction().begin();
        entityManager.merge(tipo_doc);
        entityManager.getTransaction().commit();
    }

    public void eliminarTipoDocumento(int id_tipo_doc) {
        entityManager.getTransaction().begin();
        TipoDocumento tipo_doc = entityManager.find(TipoDocumento.class, id_tipo_doc);
        entityManager.remove(tipo_doc);
        entityManager.getTransaction().commit();
    }

    public List<TipoDocumento> listarTipoDocumentos() {
        Query query = entityManager.createQuery("SELECT p FROM TipoDocumento p");
        return query.getResultList();
    }

    public TipoDocumento buscarTipoDocumentoPorId(int id_tipo_doc) {
        return entityManager.find(TipoDocumento.class, id_tipo_doc);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}