package dao;

import org.hibernate.QueryException;
import py.com.progweb.prueba.model.ReglaAsignacionPunto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.List;

@Stateless
public class ReglaAsignacion {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(ReglaAsignacionPunto entidad) {

        int limiteInferior = entidad.getLimiteInferior();
        int limiteSuperior = entidad.getLimiteSuperior();

        Query q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior<= ?1 AND c.limiteSuperior >= ?2");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        List<ReglaAsignacionPunto> coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limite inferior en conflicto con regla Nro " + coincidencias.get(0).getId());

        q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior<= ?1 AND c.limiteSuperior >= ?2");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limite superior en conflicto con regla Nro " + coincidencias.get(0).getId());

        q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior>= ?1 AND c.limiteSuperior <= ?2");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limites en conflicto con regla Nro " + coincidencias.get(0).getId());

        this.em.persist(entidad);
    }

    public List<ReglaAsignacionPunto> lista() {
        Query q = this.em.createQuery("select p from ReglaAsignacionPunto p");
        return (List<ReglaAsignacionPunto>)q.getResultList();
    }

    public void update(ReglaAsignacionPunto entidad, int id) throws ParseException {

        int limiteInferior = entidad.getLimiteInferior();
        int limiteSuperior = entidad.getLimiteSuperior();

        Query q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior<= ?1 AND c.limiteSuperior >= ?2 AND NOT c.id = ?3");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        q.setParameter(3, id);
        List<ReglaAsignacionPunto> coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limite inferior en conflicto con regla Nro " + coincidencias.get(0).getId());

        q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior<= ?1 AND c.limiteSuperior >= ?2 AND NOT c.id = ?3");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        q.setParameter(3, id);
        coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limite superior en conflicto con regla Nro " + coincidencias.get(0).getId());

        q = this.em.createQuery("select c from ReglaAsignacionPunto c WHERE c.limiteInferior>= ?1 AND c.limiteSuperior <= ?2 AND NOT c.id = ?3");
        q.setParameter(1, limiteInferior);
        q.setParameter(2, limiteSuperior);
        q.setParameter(3, id);
        coincidencias = (List<ReglaAsignacionPunto>)q.getResultList();
        if(!coincidencias.isEmpty())
            throw new QueryException("Limites en conflicto con regla Nro " + coincidencias.get(0).getId());

        ReglaAsignacionPunto e = this.em.find(ReglaAsignacionPunto.class, id);

        this.em.merge(e);

    }

    public void delete(int id){

        ReglaAsignacionPunto e = this.em.find(ReglaAsignacionPunto.class, id);

        Query q = this.em.createQuery("DELETE from ReglaAsignacionPunto c WHERE c.id =" + id);
        q.executeUpdate();

    }

}
