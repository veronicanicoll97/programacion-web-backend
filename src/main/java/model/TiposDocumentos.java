package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tipos_documentos", schema = "public", catalog = "programacion-web-backend")
public class TiposDocumentos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_tipo_documento")
    private int idTipoDocumento;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "tiposDocumentosByIdTipoDocumento")
    private Collection<Clientes> clientesByIdTipoDocumento;

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
        TiposDocumentos that = (TiposDocumentos) o;
        return idTipoDocumento == that.idTipoDocumento && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipoDocumento, descripcion);
    }

    public Collection<Clientes> getClientesByIdTipoDocumento() {
        return clientesByIdTipoDocumento;
    }

    public void setClientesByIdTipoDocumento(Collection<Clientes> clientesByIdTipoDocumento) {
        this.clientesByIdTipoDocumento = clientesByIdTipoDocumento;
    }
}
