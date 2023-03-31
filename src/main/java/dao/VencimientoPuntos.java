package dao;

import org.hibernate.QueryException;
import py.com.progweb.prueba.model.VencimientoPuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Stateless
public class VencimientoPuntos {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(VencimientoPuntos entidad) throws ParseException, QueryException {

        // Format para pasar de String a date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = new Date(format.parse(entidad.getFechaInicio()).getTime());
        Date fechaFin = new Date(format.parse(entidad.getFechaFin()).getTime());

        Query q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio<= ?1 AND c.fechaFin >= ?2");
        q.setParameter(1, fechaInicio);
        q.setParameter(2, fechaInicio);
        List<VencimientoPuntos> coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha de inicio en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio<= ?1 AND c.fechaFin >= ?2");
        q.setParameter(1, fechaFin);
        q.setParameter(2, fechaFin);
        coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha de fin en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio>= ?1 AND c.fechaFin <= ?2");
        q.setParameter(1, fechaInicio);
        q.setParameter(2, fechaFin);
        coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        int days = (int) ((format.parse(entidad.getFechaFin()).getTime()-format.parse(entidad.getFechaInicio()).getTime())/86400000);
        entidad.setDiasDuracion(days);
        this.em.persist(entidad);

    }

    public List<VencimientoPuntos> lista() {
        Query q = this.em.createQuery("select c from VencimientoPuntos c");

        return (List<VencimientoPuntos>)q.getResultList();
    }

    public void update(VencimientoPuntos entidad, int id) throws ParseException {
        // Format para pasar de String a date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaInicio = new Date(format.parse(entidad.getFechaInicio()).getTime());
        Date fechaFin = new Date(format.parse(entidad.getFechaFin()).getTime());

        Query q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio<= ?1 AND c.fechaFin >= ?2 AND NOT c.idVencimiento = ?3");
        q.setParameter(1, fechaInicio);
        q.setParameter(2, fechaInicio);
        q.setParameter(3, id);
        List<VencimientoPuntos> coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha de inicio en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio<= ?1 AND c.fechaFin >= ?2 AND NOT c.idVencimiento = ?3");
        q.setParameter(1, fechaFin);
        q.setParameter(2, fechaFin);
        q.setParameter(3, id);
        coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha de fin en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        q = this.em.createQuery("select c from VencimientoPuntos c WHERE c.fechaInicio>= ?1 AND c.fechaFin <= ?2 AND NOT c.idVencimiento = ?3");
        q.setParameter(1, fechaInicio);
        q.setParameter(2, fechaFin);
        q.setParameter(3, id);
        coincidencias = (List<VencimientoPuntos>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Fecha en conflicto con fecha de vencimiento Nro " + coincidencias.get(0).getIdVencimiento());

        VencimientoPuntos e = this.em.find(VencimientoPuntos.class, id);
        e.setFechaInicio(new Date(format.parse(entidad.getFechaInicio()).getTime()));
        e.setFechaFin(new Date(format.parse(entidad.getFechaFin()).getTime()));
        e.setDiasDuracion((int) ((format.parse(entidad.getFechaFin()).getTime()-format.parse(entidad.getFechaInicio()).getTime())/86400000) );

        this.em.merge(e);

    }

    public void delete(int id){

        VencimientoPuntos e = this.em.find(VencimientoPuntos.class, id);

        Query q = this.em.createQuery("DELETE from VencimientoPuntos c WHERE c.idVencimiento =" + id);
        q.executeUpdate();

    }

}
