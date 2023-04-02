package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "clientes", schema = "public", catalog = "backdb")
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellido")
    private String apellido;
    @Basic
    @Column(name = "nro_documento")
    private String nroDocumento;
    @Basic
    @Column(name = "id_tipo_documento")
    private int idTipoDocumento;
    @Basic
    @Column(name = "id_pais")
    private int idPais;
    @Basic
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "email")
    private String email;



    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente clientes = (Cliente) o;
        return idCliente == clientes.idCliente && idTipoDocumento == clientes.idTipoDocumento && idPais == clientes.idPais && Objects.equals(nombre, clientes.nombre) && Objects.equals(apellido, clientes.apellido) && Objects.equals(nroDocumento, clientes.nroDocumento) && Objects.equals(fechaNacimiento, clientes.fechaNacimiento) && Objects.equals(telefono, clientes.telefono) && Objects.equals(email, clientes.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombre, apellido, nroDocumento, idTipoDocumento, idPais, fechaNacimiento, telefono, email);
    }
}
