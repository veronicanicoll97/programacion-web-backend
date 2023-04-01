package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tipos_documentos", schema = "public", catalog = "backdb")
public class TipoDocumento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipo_documento")
    private int idTipoDocumento;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "tipoDocumentosByIdTipoDocumento")
    private Collection<Cliente> clienteByIdTipoDocumento;

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDocumento that = (TipoDocumento) o;
        return idTipoDocumento == that.idTipoDocumento && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoDocumento, descripcion);
    }

    public Collection<Cliente> getClientesByIdTipoDocumento() {
        return clienteByIdTipoDocumento;
    }

    public void setClientesByIdTipoDocumento(Collection<Cliente> clienteByIdTipoDocumento) {
        this.clienteByIdTipoDocumento = clienteByIdTipoDocumento;
    }
}
