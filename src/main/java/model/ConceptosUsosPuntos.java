package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "conceptos_usos_puntos", schema = "public", catalog = "programacion-web-backend")
public class ConceptosUsosPuntos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_concepto")
    private int idConcepto;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "puntos_requeridos")
    private int puntosRequeridos;
    @OneToMany(mappedBy = "conceptosUsosPuntosByIdConcepto")
    private Collection<Cabeceras> cabecerasByIdConcepto;

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(int puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConceptosUsosPuntos that = (ConceptosUsosPuntos) o;
        return idConcepto == that.idConcepto && puntosRequeridos == that.puntosRequeridos && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConcepto, descripcion, puntosRequeridos);
    }

    public Collection<Cabeceras> getCabecerasByIdConcepto() {
        return cabecerasByIdConcepto;
    }

    public void setCabecerasByIdConcepto(Collection<Cabeceras> cabecerasByIdConcepto) {
        this.cabecerasByIdConcepto = cabecerasByIdConcepto;
    }
}
