package dao;


public class DetalleDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(DetalleDAO entidad) {

        this.em.persist(entidad);

    }

}
