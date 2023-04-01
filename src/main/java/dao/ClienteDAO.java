package dao;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Stateless
public class ClienteDAO {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(ClienteDAO entidad) {

        this.em.persist(entidad);

    }

    public List<ClienteDAO> lista() {
        Query q = this.em.createQuery("select c from clientes c");

        return (List<ClienteDAO>)q.getResultList();
    }

    public void update(ClienteDAO entidad, int id) throws ParseException {

        ClienteDAO e = this.em.find(ClienteDAO.class, id);
        e.setnombre(entidad.getnombre());
        e.setapellido(entidad.getapellido());
        e.setnro_documneto(entidad.getnro_documneto());
        e.setid_tipo_Documento(entidad.getid_tipo_documento());
        e.setid_pais(entidad.getid_pais());
        e.setid_contacto(entidad.getid_contacto());

        // De cadena a Date de SQL
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(e.getfecha_nacimiento());
        Date sqlDate = new Date(date.getTime());
        e.setfecha_nacimiento(sqlDate);

        this.em.merge(e);

    }

    public void delete(int id){

        ClienteDAO e = this.em.find(ClienteDAO.class, id);

        Query q = this.em.createQuery("DELETE from clientes c WHERE c.id_cliente =" + id);
        q.executeUpdate();

    }

}
