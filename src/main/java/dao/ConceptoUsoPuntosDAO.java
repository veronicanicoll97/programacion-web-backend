package dao;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.util.List;

@Stateless
public class ConceptoUsoPuntosDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(ConceptoUsoPuntosDAO entidad) {

        this.em.persist(entidad);

    }

    public List<ConceptoUsoPuntosDAO> lista() {
        Query q = this.em.createQuery("select c from concepto_usos_puntos c");

        return (List<concepto_usos_puntos>)q.getResultList();
    }

    public void update(ConceptoUsoPuntosDAO entidad, int id) throws ParseException {

        id_concepto e = this.em.find(concepto_usos_puntos.class, id);
        e.setdescripcion(entidad.getdescripcion());
        e.setpuntos_requeridos(entidad.getpuntos_requeridos());

        this.em.merge(e);

    }

    public void delete(int id){

        Query q = this.em.createQuery("DELETE from concepto_usos_puntos c WHERE c.id_concepto =" + id);
        q.executeUpdate();

    }

    public ConceptoUsoPuntosDAO searchById(int id){

        Query q = this.em.createQuery("select c from concepto_usos_puntos c WHERE c.idConceptoUsoPuntos =" + id);
        return (ConceptoUsoPuntosDAO) q.getResultList();

    }

}
