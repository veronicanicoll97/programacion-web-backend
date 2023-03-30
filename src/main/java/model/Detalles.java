package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Detalles {
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
    private double idBolsa;
    @ManyToOne
    @JoinColumn(name = "id_cabecera", referencedColumnName = "id_cabecera", nullable = false, insertable = false, updatable = false)
    private Cabeceras cabecerasByIdCabecera;
    @ManyToOne
    @JoinColumn(name = "id_bolsa", referencedColumnName = "id_bolsa", nullable = false, insertable = false, updatable = false)
    private BolsasPuntosEntity bolsasPuntosByIdBolsa;

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

    public double getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(double idBolsa) {
        this.idBolsa = idBolsa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Detalles detalles = (Detalles) o;
        return idDetalle == detalles.idDetalle && idCabecera == detalles.idCabecera && puntajeUtilizado == detalles.puntajeUtilizado && Double.compare(detalles.idBolsa, idBolsa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalle, idCabecera, puntajeUtilizado, idBolsa);
    }

    public Cabeceras getCabecerasByIdCabecera() {
        return cabecerasByIdCabecera;
    }

    public void setCabecerasByIdCabecera(Cabeceras cabecerasByIdCabecera) {
        this.cabecerasByIdCabecera = cabecerasByIdCabecera;
    }

    public BolsasPuntosEntity getBolsasPuntosByIdBolsa() {
        return bolsasPuntosByIdBolsa;
    }

    public void setBolsasPuntosByIdBolsa(BolsasPuntosEntity bolsasPuntosByIdBolsa) {
        this.bolsasPuntosByIdBolsa = bolsasPuntosByIdBolsa;
    }
}
