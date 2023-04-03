package dao;

import model.BolsaPunto;
import model.Cabecera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class BolsaPuntoDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public BolsaPuntoDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void abrirEntity(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pruebaPU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void insertarBolsaPunto(BolsaPunto bolsa) {
        entityManager.getTransaction().begin();
        entityManager.persist(bolsa);
        entityManager.getTransaction().commit();
    }

    public void actualizarBolsaPunto(BolsaPunto bolsa) {
        entityManager.getTransaction().begin();
        entityManager.merge(bolsa);
        entityManager.getTransaction().commit();
    }

    public void eliminarBolsaPunto(int idBolsaPunto) {
        entityManager.getTransaction().begin();
        BolsaPunto bolsa = entityManager.find(BolsaPunto.class, idBolsaPunto);
        entityManager.remove(bolsa);
        entityManager.getTransaction().commit();
    }

    public List<BolsaPunto> listarBolsaPuntoes() {
        Query query = entityManager.createQuery("SELECT p FROM BolsaPunto p");
        return query.getResultList();
    }

    public List<BolsaPunto> listarBolsaPuntoByIdCliente(int clienteId) {
        Query query = entityManager.createQuery("SELECT p FROM BolsaPunto p where p.idCliente = :clienteId and p.saldoPuntos > 0 order by p.fechaAsignacionPunto, p.idBolsa");
        query.setParameter("clienteId", clienteId);
        return query.getResultList();
    }

    public BolsaPunto buscarBolsaPuntoPorId(int idBolsaPunto) {
        return entityManager.find(BolsaPunto.class, idBolsaPunto);
    }

    public void cerrarEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public List<BolsaPunto> listarBolsaCliente(int idCliente) {
        Query query = entityManager.createQuery("SELECT p FROM BolsaPunto p where p.idCliente = :clienteId ");
        query.setParameter("clienteId", idCliente);
        return query.getResultList();
    }

    public List<BolsaPunto> listarBolsaRangoPuntos(int limiteInf, int limiteSup) {
        Query query = entityManager.createQuery("SELECT p FROM BolsaPunto p where p.puntajeAsignado BETWEEN :limiteInf AND :limiteSup");
        query.setParameter("limiteInf", limiteInf);
        query.setParameter("limiteSup", limiteSup);
        return query.getResultList();
    }

    public List<BolsaPunto> puntosPorVencerByCliente() {
        Query query = entityManager.createQuery("SELECT p FROM BolsaPunto p WHERE p.saldoPuntos = 0");

        return query.getResultList();
    }
}