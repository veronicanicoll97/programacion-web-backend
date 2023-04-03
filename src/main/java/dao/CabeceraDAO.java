package dao;

import model.Cabecera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public List<Cabecera> listarCabeceraCliente(int idCliente) {
        Query query = entityManager.createQuery("SELECT p FROM Cabecera p where p.idCliente = :clienteId ");
        query.setParameter("clienteId", idCliente);
        return query.getResultList();
    }

    public List<Cabecera> listarCabeceraIdConcepto(int idConcepto) {
        Query query = entityManager.createQuery("SELECT p FROM Cabecera p where p.idConcepto = :idConcepto ");
        query.setParameter("idConcepto", idConcepto);
        return query.getResultList();
    }

    public List<Cabecera> listarCabeceraFecha(String fechaUso) {
        java.util.Date fechaUtil = null;
        try {
            fechaUtil = new SimpleDateFormat("yyyy-MM-dd").parse(fechaUso);
        }catch (Exception e){

        }
        java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
        Query query = entityManager.createQuery("SELECT p FROM Cabecera p where p.fechaUso = :fechaSql ");
        query.setParameter("fechaSql", fechaSql);
        return query.getResultList();
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
}