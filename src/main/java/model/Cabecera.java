package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cabeceras", schema = "public", catalog = "backdb")
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
        Cabecera cabeceras = (Cabecera) o;
        return idCabecera == cabeceras.idCabecera && idCliente == cabeceras.idCliente && puntajeUtilizado == cabeceras.puntajeUtilizado && idConcepto == cabeceras.idConcepto && Objects.equals(fechaUso, cabeceras.fechaUso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCabecera, idCliente, puntajeUtilizado, fechaUso, idConcepto);
    }


}
