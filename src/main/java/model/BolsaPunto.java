package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "bolsas_puntos", schema = "public", catalog = "backdb")
public class BolsaPunto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_bolsa")
    private int idBolsa;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "fecha_asignacion_punto")
    private Date fechaAsignacionPunto;
    @Basic
    @Column(name = "fecha_caducidad_punto")
    private Date fechaCaducidadPunto;
    @Basic
    @Column(name = "puntaje_asignado")
    private int puntajeAsignado;
    @Basic
    @Column(name = "puntaje_utilizado")
    private int puntajeUtilizado;
    @Basic
    @Column(name = "saldo_puntos")
    private int saldoPuntos;
    @Basic
    @Column(name = "monto_operacion")
    private double montoOperacion;

    public int getIdBolsa() {
        return idBolsa;
    }

    public void setIdBolsa(int idBolsa) {
        this.idBolsa = idBolsa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaAsignacionPunto() {
        return fechaAsignacionPunto;
    }

    public void setFechaAsignacionPunto(Date fechaAsignacionPunto) {
        this.fechaAsignacionPunto = fechaAsignacionPunto;
    }

    public Date getFechaCaducidadPunto() {
        return fechaCaducidadPunto;
    }

    public void setFechaCaducidadPunto(Date fechaCaducidadPunto) {
        this.fechaCaducidadPunto = fechaCaducidadPunto;
    }

    public int getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(int puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public int getSaldoPuntos() {
        return saldoPuntos;
    }

    public void setSaldoPuntos(int saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }

    public double getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(double montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BolsaPunto that = (BolsaPunto) o;
        return idBolsa == that.idBolsa && idCliente == that.idCliente && puntajeAsignado == that.puntajeAsignado && puntajeUtilizado == that.puntajeUtilizado && saldoPuntos == that.saldoPuntos && Double.compare(that.montoOperacion, montoOperacion) == 0 && Objects.equals(fechaAsignacionPunto, that.fechaAsignacionPunto) && Objects.equals(fechaCaducidadPunto, that.fechaCaducidadPunto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBolsa, idCliente, fechaAsignacionPunto, fechaCaducidadPunto, puntajeAsignado, puntajeUtilizado, saldoPuntos, montoOperacion);
    }

}
