package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DetalleBolsaPuntosManyToMany {

    @PersistenceContext(unitName="pruebaPU")
    private EntityManager em;

    public void agregar(BolsaPuntosDAO entidad){
        this.em.persist(entidad);
    }


}
