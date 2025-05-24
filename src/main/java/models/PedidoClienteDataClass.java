package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PedidoClienteDataClass implements Serializable {

    private int idCliente;
    private String email;
    private String nombre;
    private String localidad;
    private String provincia;
    private String direccion;
    private int movil;
    private int idPedido;
    private LocalDate fechaPedido;
    private LocalDate fechaEntregaEstimada;
    private int estado;
    private String comentario;
    private ArrayList<Producto> productos;

    // Constructor
    public PedidoClienteDataClass(int idCliente, String email, String nombre, String localidad, String provincia, String direccion, int movil, int idPedido, LocalDate fechaPedido, LocalDate fechaEntregaEstimada, int estado, String comentario, ArrayList<Producto> productos) {
        this.idCliente = idCliente;
        this.email = email;
        this.nombre = nombre;
        this.localidad = localidad;
        this.provincia = provincia;
        this.direccion = direccion;
        this.movil = movil;
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.estado = estado;
        this.comentario = comentario;
        this.productos = productos;
    }

    public PedidoClienteDataClass(Cliente c, Pedido p) {
        this.idCliente = c.getId();
        this.email = c.getEmail();
        this.nombre = c.getNombre();
        this.localidad = c.getLocalidad();
        this.provincia = c.getProvincia();
        this.direccion = c.getDireccion();
        this.movil = c.getMovil();
        this.idPedido = p.getId();
        this.fechaPedido = p.getFechaPedido();
        this.fechaEntregaEstimada = p.getFechaEntregaEstimada();
        this.estado = p.getEstado();
        this.comentario = p.getComentario();
        this.productos = p.getProductos();
    }

    //Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    // Otros metodos
    @Override
    public String toString() {
        return " *************** " + idPedido + " *************** \n" +
                "Estado del pedido: " + devuelveEstado(estado) + "\n" +
                "Nombre del cliente: " + nombre + "\n" +
                "Dirección de entrega: " + direccion + "\n" +
                "Localidad: " + localidad + "\n" +
                "Provincia: " + provincia + "\n" +
                "Teléfono del cliente: " + movil + "\n" +
                "Fecha del pedido: " + fechaPedido + "\n" +
                "Fecha de entrega estimada: " + fechaEntregaEstimada + "\n" +
                "Comentarios del pedido: " + comentario + "\n" +
                "Detalles del pedido: \n" +
                pintaProductos(productos);
    }

    //Metodo que devuelve el estado de un Pedido
    public String devuelveEstado(int estado) {
        return switch (estado) {
            case 0 -> "Creado";
            case 1 -> "En preparación";
            case 2 -> "Enviado";
            case 3 -> "Entregado";
            case 4 -> "Cancelado";
            default -> "";
        };

    }

    //Metodo que pinta productos
    private String pintaProductos(ArrayList<Producto> productos) {
        String resultado = "";

        for (Producto p : productos) {
            resultado += p.pintaProductoADetalle() + "\n";
        }
        return resultado;
    }

}