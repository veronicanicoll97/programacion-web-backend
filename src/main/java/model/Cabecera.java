package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Cabecera {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cabecera")
    private int idCabecera;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;
    @Basic
    @Column(name = "fecha_uso")
    private Date fechaUso;
    @Basic
    @Column(name = "id_concepto")
    private int idConcepto;
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false,  insertable = false, updatable = false)
    private Cliente clienteByIdCliente;
    @ManyToOne
    @JoinColumn(name = "id_concepto", referencedColumnName = "id_concepto", nullable = false,  insertable = false, updatable = false)
    private ConceptosUsosPuntos conceptosUsosPuntosByIdConcepto;
    @OneToMany(mappedBy = "cabeceraByIdCabecera")
    private Collection<Detalle> detalleByIdCabecera;

    public int getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(int idCabecera) {
        this.idCabecera = idCabecera;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Date getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(Date fechaUso) {
        this.fechaUso = fechaUso;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cabecera cabecera = (Cabecera) o;
        return idCabecera == cabecera.idCabecera && idCliente == cabecera.idCliente && puntajeUtilizado == cabecera.puntajeUtilizado && idConcepto == cabecera.idConcepto && Objects.equals(fechaUso, cabecera.fechaUso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCabecera, idCliente, puntajeUtilizado, fechaUso, idConcepto);
    }

    public Cliente getClientesByIdCliente() {
        return clienteByIdCliente;
    }

    public void setClientesByIdCliente(Cliente clienteByIdCliente) {
        this.clienteByIdCliente = clienteByIdCliente;
    }

    public ConceptosUsosPuntos getConceptosUsosPuntosByIdConcepto() {
        return conceptosUsosPuntosByIdConcepto;
    }

    public void setConceptosUsosPuntosByIdConcepto(ConceptosUsosPuntos conceptosUsosPuntosByIdConcepto) {
        this.conceptosUsosPuntosByIdConcepto = conceptosUsosPuntosByIdConcepto;
    }

    public Collection<Detalle> getDetallesByIdCabecera() {
        return detalleByIdCabecera;
    }

    public void setDetallesByIdCabecera(Collection<Detalle> detalleByIdCabecera) {
        this.detalleByIdCabecera = detalleByIdCabecera;
    }
}
