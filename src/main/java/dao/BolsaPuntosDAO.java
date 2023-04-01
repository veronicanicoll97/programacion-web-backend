package dao;
import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


@Entity
@Table(name="bolsa_puntos")

public class BolsaPuntosDAO {

    //Column: como se llama la columna en la base de datos
    //Basic: algunas restricciones o temas a tener en cuenta

    // primary key
    @Id
    @Column(name="id_bolsa")
    @Basic(optional = false)
    @GeneratedValue(generator = "bolsaPuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bolsaPuntosSec", sequenceName = "sec_bolsa_puntos", allocationSize = 0)
    private Integer id_bolsa_puntos;

    // foreign key
    @Column(name="id_cliente")
    @Basic(optional = false)
    private Integer id_cliente;


    // fecha de asignacion de los puntos
    @Column(name="fecha_asignacion_punto")
    @Basic(optional = false)
    private Date fecha_asignacion_punto;

    // fecha de vencimiento de los puntos asignados
    @Column(name="fecha_caducidad_punto")
    @Basic(optional = false)
    private Date fecha_caducidad_punto;

    // total de puntos asignados por operacion
    @Column(name="puntaje_asignado")
    @Basic(optional = false)
    private Integer puntaje_asignado;

    // total de puntos que ya ha utilizado
    @Column(name="puntaje_utilizado")
    @Basic(optional = false)
    private Integer puntaje_utilizado;

    // total - utilizado
    @Column(name="saldo_puntos")
    @Basic(optional = false)
    private Integer saldo_puntos;

    // monto pagado para conseguir estos puntos
    @Column(name="monto_operacion")
    @Basic(optional = false)
    private Integer monto_operacion;

    //constructor sin parámetros
    public BolsaPuntosDAO(){

    }

    // GETTER AND SETTER

    public Integer getId_bolsa_puntos() {
        return id_bolsa_puntos;
    }

    public void setId_bolsa_puntos(Integer id_bolsa_puntos) {
        this.id_bolsa_puntos = id_bolsa_puntos;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFecha_asignacion() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(fecha_asignacion_punto);
        return strDate;
    }

    public void setFecha_asignacion(Date fecha_asignacion) {
        this.fecha_asignacion_punto = fecha_asignacion_punto;
    }

    public String getFecha_caducidad() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(fecha_caducidad_punto);
        return strDate;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad_punto = fecha_caducidad_punto;
    }

    public Integer getPuntos_totales() {
        return puntaje_asignado;
    }

    public void setPuntos_totales(Integer puntos_totales) {
        this.puntaje_asignado= puntaje_asignado;
    }

    public Integer getPuntos_utilizados() {
        return puntaje_utilizado;
    }

    public void setPuntos_utilizados(Integer puntos_utilizados) {
        this.puntaje_utilizado = puntaje_utilizado;
    }

    public Integer getSaldo_puntos() {
        return saldo_puntos;
    }

    public void setSaldo_puntos(Integer saldo_puntos) {
        this.saldo_puntos = saldo_puntos;
    }

    public Integer getMonto_operacion() {
        return monto_operacion;
    }

    public void setMonto_operacion(Integer monto_operacion) {
        this.monto_operacion = monto_operacion;
    }

    public Object listaCliente(Integer idCliente) {
        return null;
    }
}
