package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reglas_asignaciones_puntos", schema = "public", catalog = "backdb")
public class ReglaAsignacionPunto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_asignacion")
    private int idAsignacion;
    @Basic
    @Column(name = "limite_inferior")
    private double limiteInferior;
    @Basic
    @Column(name = "limite_superior")
    private double limiteSuperior;
    @Basic
    @Column(name = "monto_equivalencia_punto")
    private int montoEquivalenciaPunto;

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public int getMontoEquivalenciaPunto() {
        return montoEquivalenciaPunto;
    }

    public void setMontoEquivalenciaPunto(int montoEquivalenciaPunto) {
        this.montoEquivalenciaPunto = montoEquivalenciaPunto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReglaAsignacionPunto that = (ReglaAsignacionPunto) o;
        return idAsignacion == that.idAsignacion && Double.compare(that.limiteInferior, limiteInferior) == 0 && Double.compare(that.limiteSuperior, limiteSuperior) == 0 && montoEquivalenciaPunto == that.montoEquivalenciaPunto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAsignacion, limiteInferior, limiteSuperior, montoEquivalenciaPunto);
    }
}
