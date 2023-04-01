package dao;


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

    public void agregar(UsoPuntosDAO entidad) {

        puntosDAO = new ConceptoUsoPuntosDAO();
        bolsaPuntosDAO = new BolsaPuntosDAO();
        ConceptoUsoPuntosDAO concepto = this.em.find(ConceptoUsoPuntosDAO.class, entidad.getid_concepto());
        if(concepto == null)
            throw new QueryException("No existe un concepto registrado con ese id");
        // Cantidad de puntos a utilizar
        int puntos_requeridos = concepto.getpuntos_requeridos();
        int puntos_utilizados = 0;

        // Busqueda de cliente
        ClienteDAO clienteDAO = this.em.find(ClienteDAO.class, entidad.getid_cliente());
        if(clienteDAO == null)
            throw new QueryException("No existe el cliente");

        // Bolsas disponibles para uso
        Query q = this.em.createQuery("select b from bolsas_puntosDAO b where b.id_cliente = ?1 and b.saldo_puntos > ?2 order by b.fecha_asignacion_punto asc, b.id_bolsa asc " );
        q.setParameter(1, entidad.getid_cliente());
        q.setParameter(2, 0);
        List<BolsaPuntosDAO> bolsas = (List<BolsaPuntosDAO>)q.getResultList();

        // Lista de bolsas uilizadas
        ArrayList<BolsaPuntosDAO> bolsasUtilizadas = new ArrayList<>();

        // Buscar todas las bolsas a utilizar para los puntos necesarios
        for(BolsaPuntosDAO b : bolsas){
            bolsasUtilizadas.add(b);
            puntos_utilizados+= b.getsaldo_puntos();
            if(puntos_utilizados >= puntos_requeridos){
                break;
            }

        }

        if(puntos_utilizados < puntos_requeridos){
            throw new QueryException("No existen puntos suficientes");
        }

        // Crear cabecera
        Cabecera cabecera = new Cabecera();
        cabecera.setid_cliente(entidad.getid_cliente());
        cabecera.setpuntaje_utilizado(puntos_requeridos);
        cabecera.setid_concepto(entidad.getid_concepto());
        cabecera.setFechaUso(Date.valueOf(java.time.LocalDate.now()));
        this.em.persist(cabecera);

        // Crear detalle
        DetalleDAO detalleDAO = new DetalleDAO();
        detalleDAO.setid_cabecera(cabecera.getid_cabecera());
        detalleDAO.setpuntajeutilizado(puntos_requeridos);
        this.em.persist(detalleDAO);
        puntosUtilizados = 0;
        // Consumir puntos de las bolsas
        for(BolsaPuntosDAO b : bolsasUtilizadas){
            if(b.getSaldo_puntos() - puntosRequeridos < 0){
                BolsaPuntosDAO e = this.em.find(BolsaPuntosDAO.class, b.getId_bolsa_puntos());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + b.getSaldo_puntos());
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());

                this.em.merge(e);
            }
            else {
                BolsaPuntosDAO e = this.em.find(BolsaPuntosDAO.class, b.getId_bolsa_puntos());
                puntosUtilizados = e.getSaldo_puntos();
                e.setPuntos_utilizados(e.getPuntos_utilizados() + puntosRequeridos);
                e.setSaldo_puntos(e.getPuntos_totales() - e.getPuntos_utilizados());

                this.em.merge(e);
            }
            // Enlazar detalle con bolsas utilizadas
            DetalleBolsaPuntosManytoMany m2m = new DetalleBolsaPuntosManytoMany();
            m2m.setIdDetalle(detalleDAO.getIdDetalle());
            m2m.setIdBolsaPuntos(b.getId_bolsa_puntos());
            this.em.persist(m2m);
            puntosRequeridos-= puntosUtilizados;
        }

    }

}
