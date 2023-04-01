package dao;


import com.tp.tpback.UsoPuntos;
import model.*;
import org.eclipse.persistence.exceptions.QueryException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsoPuntosDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    ConceptoUsoPuntosDAO puntosDAO;
    BolsaPuntosDAO bolsaPuntosDAO;

    public void agregar(ConceptosUsosPuntos entidad, Cliente cliente) throws Exception {
        puntosDAO = new ConceptoUsoPuntosDAO();
        bolsaPuntosDAO = new BolsaPuntosDAO();
        ConceptosUsosPuntos concepto = this.em.find(ConceptosUsosPuntos.class, entidad.getIdConcepto());
        if(concepto == null)
            throw new Exception("No existe un concepto registrado con ese id");
        // Cantidad de puntos a utilizar
        int puntos_requeridos = concepto.getPuntosRequeridos();
        int puntos_utilizados = 0;

        // Bolsas disponibles para uso
        Query q = this.em.createQuery("select b from BolsasPuntosEntity b where b.idCliente = ?1 and b.saldoPuntos > 0 order by b.fechaAsignacionPunto asc, b.idBolsa asc " );
        q.setParameter(1, cliente.getIdCliente());
        List<BolsasPuntosEntity> bolsas = (List<BolsasPuntosEntity>)q.getResultList();

        // Lista de bolsas uilizadas
        ArrayList<BolsasPuntosEntity> bolsasUtilizadas = new ArrayList<>();

        // Buscar todas las bolsas a utilizar para los puntos necesarios
        for(BolsasPuntosEntity b : bolsas){
            bolsasUtilizadas.add(b);
            puntos_utilizados+= b.getSaldoPuntos();
            if(puntos_utilizados >= puntos_requeridos){
                break;
            }
        }

        if(puntos_utilizados < puntos_requeridos){
            throw new Exception("No existen puntos suficientes");
        }

        // Crear cabecera
        Cabecera cabecera = new Cabecera();
        cabecera.setIdCliente(cliente.getIdCliente());
        cabecera.setPuntajeUtilizado(puntos_requeridos);
        cabecera.setIdConcepto(entidad.getIdConcepto());
        cabecera.setFechaUso(Date.valueOf(java.time.LocalDate.now()));
        this.em.persist(cabecera);

        // Crear detalle
        /*Detalle detalle = new Detalle();
        detalle.setCabecerasByIdCabecera(cabecera);
        detalle.setPuntajeUtilizado(puntos_requeridos);
        this.em.persist(detalle);*/
        int puntosUtilizados = 0;
        // Consumir puntos de las bolsas
        for(BolsasPuntosEntity b : bolsasUtilizadas){
            if(b.getSaldoPuntos() - puntos_requeridos < 0){
                BolsaPuntosDAO e = this.em.find(BolsaPuntosDAO.class, b.getIdBolsa());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + b.getSaldoPuntos());
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());
                this.em.merge(e);
            }
            else {
                BolsaPuntosDAO e = this.em.find(BolsaPuntosDAO.class, b.getIdBolsa());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + puntos_requeridos);
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());
                this.em.merge(e);
            }
            Detalle detalle = new Detalle();
            detalle.setCabecerasByIdCabecera(cabecera);
            detalle.setPuntajeUtilizado(puntosUtilizados);
            detalle.setBolsasPuntosByIdBolsa(b);
            this.em.persist(detalle);
            puntos_requeridos -= puntosUtilizados;
        }
    }

}
