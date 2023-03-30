package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Clientes {
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
    @Column(name = "id_contacto")
    private int idContacto;
    @OneToMany(mappedBy = "clientesByIdCliente")
    private Collection<BolsasPuntosEntity> bolsasPuntosByIdCliente;
    @OneToMany(mappedBy = "clientesByIdCliente")
    private Collection<Cabeceras> cabecerasByIdCliente;
    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento", nullable = false,  insertable = false, updatable = false)
    private TiposDocumentos tiposDocumentosByIdTipoDocumento;
    @ManyToOne
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais", nullable = false,  insertable = false, updatable = false)
    private Pais paisByIdPais;
    @ManyToOne
    @JoinColumn(name = "id_contacto", referencedColumnName = "id_contacto", nullable = false,  insertable = false, updatable = false)
    private Contactos contactosByIdContacto;

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

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clientes clientes = (Clientes) o;
        return idCliente == clientes.idCliente && idTipoDocumento == clientes.idTipoDocumento && idPais == clientes.idPais && idContacto == clientes.idContacto && Objects.equals(nombre, clientes.nombre) && Objects.equals(apellido, clientes.apellido) && Objects.equals(nroDocumento, clientes.nroDocumento) && Objects.equals(fechaNacimiento, clientes.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombre, apellido, nroDocumento, idTipoDocumento, idPais, fechaNacimiento, idContacto);
    }

    public Collection<BolsasPuntosEntity> getBolsasPuntosByIdCliente() {
        return bolsasPuntosByIdCliente;
    }

    public void setBolsasPuntosByIdCliente(Collection<BolsasPuntosEntity> bolsasPuntosByIdCliente) {
        this.bolsasPuntosByIdCliente = bolsasPuntosByIdCliente;
    }

    public Collection<Cabeceras> getCabecerasByIdCliente() {
        return cabecerasByIdCliente;
    }

    public void setCabecerasByIdCliente(Collection<Cabeceras> cabecerasByIdCliente) {
        this.cabecerasByIdCliente = cabecerasByIdCliente;
    }

    public TiposDocumentos getTiposDocumentosByIdTipoDocumento() {
        return tiposDocumentosByIdTipoDocumento;
    }

    public void setTiposDocumentosByIdTipoDocumento(TiposDocumentos tiposDocumentosByIdTipoDocumento) {
        this.tiposDocumentosByIdTipoDocumento = tiposDocumentosByIdTipoDocumento;
    }

    public Pais getPaisByIdPais() {
        return paisByIdPais;
    }

    public void setPaisByIdPais(Pais paisByIdPais) {
        this.paisByIdPais = paisByIdPais;
    }

    public Contactos getContactosByIdContacto() {
        return contactosByIdContacto;
    }

    public void setContactosByIdContacto(Contactos contactosByIdContacto) {
        this.contactosByIdContacto = contactosByIdContacto;
    }
}
