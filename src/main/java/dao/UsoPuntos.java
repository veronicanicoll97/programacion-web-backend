package dao;

import org.hibernate.QueryException;
import py.com.progweb.prueba.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UsoPuntos {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    ConceptoUsoPuntos puntosDAO;
    BolsaPuntosDAO bolsaPuntosDAO;

    public void agregar(UsoPuntos entidad) {

        puntosDAO = new ConceptoUsoPuntos();
        bolsaPuntosDAO = new BolsaPuntosDAO();
        ConceptoUsoPuntos concepto = this.em.find(ConceptoUsoPuntos.class, entidad.getIdConcepto());
        if(concepto == null)
            throw new QueryException("No existe un concepto registrado con ese id");
        // Cantidad de puntos a utilizar
        int puntosRequeridos = concepto.getPuntosRequeridos();
        int puntosUtilizados = 0;

        // Busqueda de cliente
        Cliente cliente = this.em.find(Cliente.class, entidad.getIdCliente());
        if(cliente == null)
            throw new QueryException("No existe el cliente");

        // Bolsas disponibles para uso
        Query q = this.em.createQuery("select b from BolsaPuntos b where b.id_cliente = ?1 and b.saldo_puntos > ?2 order by b.fecha_asignacion asc, b.id_bolsa_puntos asc " );
        q.setParameter(1, entidad.getIdCliente());
        q.setParameter(2, 0);
        List<BolsaPuntos> bolsas = (List<BolsaPuntos>)q.getResultList();

        // Lista de bolsas uilizadas
        ArrayList<BolsaPuntos> bolsasUtilizadas = new ArrayList<>();

        // Buscar todas las bolsas a utilizar para los puntos necesarios
        for(BolsaPuntos b : bolsas){
            bolsasUtilizadas.add(b);
            puntosUtilizados+= b.getSaldo_puntos();
            if(puntosUtilizados >= puntosRequeridos){
                break;
            }

        }

        if(puntosUtilizados < puntosRequeridos){
            throw new QueryException("No existen puntos suficientes");
        }

        // Crear cabecera
        Cabecera cabecera = new Cabecera();
        cabecera.setIdCliente(entidad.getIdCliente());
        cabecera.setPuntajeUtilizado(puntosRequeridos);
        cabecera.setIdConceptoPuntos(entidad.getIdConcepto());
        cabecera.setFechaUso(Date.valueOf(java.time.LocalDate.now()));
        this.em.persist(cabecera);

        // Crear detalle
        Detalle detalle = new Detalle();
        detalle.setIdCabecera(cabecera.getIdCabecera());
        detalle.setPuntajeUtilizado(puntosRequeridos);
        this.em.persist(detalle);
        puntosUtilizados = 0;
        // Consumir puntos de las bolsas
        for(BolsaPuntos b : bolsasUtilizadas){
            if(b.getSaldo_puntos() - puntosRequeridos < 0){
                BolsaPuntos e = this.em.find(BolsaPuntos.class, b.getId_bolsa_puntos());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + b.getSaldo_puntos());
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());

                this.em.merge(e);
            }
            else {
                BolsaPuntos e = this.em.find(BolsaPuntos.class, b.getId_bolsa_puntos());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + puntosRequeridos);
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());

                this.em.merge(e);
            }
            // Enlazar detalle con bolsas utilizadas
            DetalleBolsaPuntosManytoMany m2m = new DetalleBolsaPuntosManytoMany();
            m2m.setIdDetalle(detalle.getIdDetalle());
            m2m.setIdBolsaPuntos(b.getId_bolsa_puntos());
            this.em.persist(m2m);
            puntosRequeridos-= puntosUtilizados;
        }

    }

}
