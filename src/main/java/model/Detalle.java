package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detalles", schema = "public", catalog = "backdb")
public class Detalle {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_detalle")
    private int idDetalle;
    @Basic
    @Column(name = "id_cabecera")
    private int idCabecera;
    @Basic
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;
    @Basic
    @Column(name = "id_bolsa")
    private int idBolsa;

    public Detalle() {
    }

    public Detalle(int idCabecera, int puntos_usados, int idBolsa) {
        this.idCabecera = idCabecera;
        this.puntajeUtilizado = puntos_usados;
        this.idBolsa = idBolsa;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(int idCabecera) {
        this.idCabecera = idCabecera;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public int getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(int idBolsa) {
        this.idBolsa = idBolsa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detalle detalles = (Detalle) o;
        return idDetalle == detalles.idDetalle && idCabecera == detalles.idCabecera && puntajeUtilizado == detalles.puntajeUtilizado && idBolsa == detalles.idBolsa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalle, idCabecera, puntajeUtilizado, idBolsa);
    }

}
