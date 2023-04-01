package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "clientes")
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
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "email")
    private String email;
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
    @OneToMany(mappedBy = "clienteByIdCliente")
    private Collection<BolsaPunto> bolsasPuntosByIdCliente;
    @OneToMany(mappedBy = "clienteByIdCliente")
    private Collection<Cabecera> cabeceraByIdCliente;
    @ManyToOne
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento", nullable = false,  insertable = false, updatable = false)
    private TipoDocumento tipoDocumentosByIdTipoDocumento;
    @ManyToOne
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais", nullable = false,  insertable = false, updatable = false)
    private Pais paisByIdPais;

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
        Cliente cliente = (Cliente) o;
        return idCliente == cliente.idCliente && idTipoDocumento == cliente.idTipoDocumento && idPais == cliente.idPais && telefono.equals(cliente.telefono) && email.equals(cliente.email) && Objects.equals(nombre, cliente.nombre) && Objects.equals(apellido, cliente.apellido) && Objects.equals(nroDocumento, cliente.nroDocumento) && Objects.equals(fechaNacimiento, cliente.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombre, apellido, nroDocumento, idTipoDocumento, idPais, fechaNacimiento, telefono, email);
    }

    public Collection<BolsaPunto> getBolsasPuntosByIdCliente() {
        return bolsasPuntosByIdCliente;
    }

    public void setBolsasPuntosByIdCliente(Collection<BolsaPunto> bolsasPuntosByIdCliente) {
        this.bolsasPuntosByIdCliente = bolsasPuntosByIdCliente;
    }

    public Collection<Cabecera> getCabecerasByIdCliente() {
        return cabeceraByIdCliente;
    }

    public void setCabecerasByIdCliente(Collection<Cabecera> cabeceraByIdCliente) {
        this.cabeceraByIdCliente = cabeceraByIdCliente;
    }

    public TipoDocumento getTiposDocumentosByIdTipoDocumento() {
        return tipoDocumentosByIdTipoDocumento;
    }

    public void setTiposDocumentosByIdTipoDocumento(TipoDocumento tipoDocumentosByIdTipoDocumento) {
        this.tipoDocumentosByIdTipoDocumento = tipoDocumentosByIdTipoDocumento;
    }

    public Pais getPaisByIdPais() {
        return paisByIdPais;
    }

    public void setPaisByIdPais(Pais paisByIdPais) {
        this.paisByIdPais = paisByIdPais;
    }


}
