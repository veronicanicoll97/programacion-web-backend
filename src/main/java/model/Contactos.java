package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Contactos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_contacto")
    private int idContacto;
    @Basic
    @Column(name = "tipo_contacto")
    private String tipoContacto;
    @Basic
    @Column(name = "valor")
    private String valor;
    @OneToMany(mappedBy = "contactosByIdContacto")
    private Collection<Cliente> clienteByIdContacto;

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contactos contactos = (Contactos) o;
        return idContacto == contactos.idContacto && Objects.equals(tipoContacto, contactos.tipoContacto) && Objects.equals(valor, contactos.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContacto, tipoContacto, valor);
    }

    public Collection<Cliente> getClientesByIdContacto() {
        return clienteByIdContacto;
    }

    public void setClientesByIdContacto(Collection<Cliente> clienteByIdContacto) {
        this.clienteByIdContacto = clienteByIdContacto;
    }
}
