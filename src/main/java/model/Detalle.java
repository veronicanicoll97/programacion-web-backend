package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detalles")
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
    private double idBolsa;
    @ManyToOne
    @JoinColumn(name = "id_cabecera", referencedColumnName = "id_cabecera", nullable = false, insertable = false, updatable = false)
    private Cabecera cabeceraByIdCabecera;
    @ManyToOne
    @JoinColumn(name = "id_bolsa", referencedColumnName = "id_bolsa", nullable = false, insertable = false, updatable = false)
    private BolsaPunto bolsasPuntosByIdBolsa;


    public Detalle() {
    }

    public Detalle(int idCabecera, int puntajeUtilizado, double idBolsa) {
        this.idCabecera = idCabecera;
        this.puntajeUtilizado = puntajeUtilizado;
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
        Detalle detalle = (Detalle) o;
        return idDetalle == detalle.idDetalle && idCabecera == detalle.idCabecera && puntajeUtilizado == detalle.puntajeUtilizado && Double.compare(detalle.idBolsa, idBolsa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalle, idCabecera, puntajeUtilizado, idBolsa);
    }

    public Cabecera getCabecerasByIdCabecera() {
        return cabeceraByIdCabecera;
    }

    public void setCabecerasByIdCabecera(Cabecera cabeceraByIdCabecera) {
        this.cabeceraByIdCabecera = cabeceraByIdCabecera;
    }

    public BolsaPunto getBolsasPuntosByIdBolsa() {
        return bolsasPuntosByIdBolsa;
    }

    public void setBolsasPuntosByIdBolsa(BolsaPunto bolsasPuntosByIdBolsa) {
        this.bolsasPuntosByIdBolsa = bolsasPuntosByIdBolsa;
    }
}
