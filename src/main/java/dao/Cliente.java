package dao;


import py.com.progweb.prueba.model.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Stateless
public class Cliente {

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    public void agregar(Cliente entidad) {

        this.em.persist(entidad);

    }

    public List<Cliente> lista() {
        Query q = this.em.createQuery("select c from Cliente c");

        return (List<Cliente>)q.getResultList();
    }

    public void update(Cliente entidad, int id) throws ParseException {

        Cliente e = this.em.find(Cliente.class, id);
        e.setNombre(entidad.getNombre());
        e.setApellido(entidad.getApellido());
        e.setNroDocumneto(entidad.getNroDocumneto());
        e.setTipoDocumento(entidad.getTipoDocumento());
        e.setNacionalidad(entidad.getNacionalidad());
        e.setEmail(entidad.getEmail());
        e.setTelefono(entidad.getTelefono());

        // De cadena a Date de SQL
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(e.getFechaNacimiento());
        Date sqlDate = new Date(date.getTime());
        e.setFechaNacimiento(sqlDate);

        this.em.merge(e);

    }

    public void delete(int id){

        Cliente e = this.em.find(Cliente.class, id);

        Query q = this.em.createQuery("DELETE from Cliente c WHERE c.idCliente =" + id);
        q.executeUpdate();

    }

}
