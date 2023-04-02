package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "vencimientos_puntos", schema = "public", catalog = "backdb")
public class VencimientoPunto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_vencimiento")
    private int idVencimiento;
    @Basic
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Basic
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Basic
    @Column(name = "dias_duracion")
    private int diasDuracion;

    public int getIdVencimiento() {
        return idVencimiento;
    }

    public void setIdVencimiento(int idVencimiento) {
        this.idVencimiento = idVencimiento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(int diasDuracion) {
        this.diasDuracion = diasDuracion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VencimientoPunto that = (VencimientoPunto) o;
        return idVencimiento == that.idVencimiento && diasDuracion == that.diasDuracion && Objects.equals(fechaInicio, that.fechaInicio) && Objects.equals(fechaFin, that.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVencimiento, fechaInicio, fechaFin, diasDuracion);
    }
}
