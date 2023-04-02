package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Table(name = "pais", schema = "public", catalog = "backdb")
@Entity
public class Pais {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pais")
    private int idPais;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "nacionalidad")
    private String nacionalidad;


    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return idPais == pais.idPais && Objects.equals(descripcion, pais.descripcion) && Objects.equals(nacionalidad, pais.nacionalidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPais, descripcion, nacionalidad);
    }


}
