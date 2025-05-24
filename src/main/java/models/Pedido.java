package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Pedido implements Serializable {

    //Atributos
    private int id;
    private LocalDate fechaPedido;
    private LocalDate fechaEntregaEstimada;
    private int estado;
    private String comentario;
    private ArrayList<Producto> productos;

    //Constructor
    public Pedido(int id, LocalDate fechaPedido, String comentario, ArrayList<Producto> productos) {
        this.id = id;
        this.fechaPedido = fechaPedido;
        this.fechaEntregaEstimada = fechaPedido.plusDays(5);
        this.estado = 0;
        this.comentario = comentario;
        this.productos = productos;
    }

    //Constructor copia
    public Pedido(Pedido otroPedido) {
        this.id = otroPedido.id;
        this.fechaPedido = otroPedido.fechaPedido;
        this.fechaEntregaEstimada = otroPedido.fechaEntregaEstimada;
        this.estado = otroPedido.estado;
        this.comentario = otroPedido.comentario;

        //Copiamos los productos
        this.productos = new ArrayList<>();
        for (Producto producto : otroPedido.productos) {
            this.productos.add(new Producto(producto));
        }
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    //Otros metodos

    @Override
    public String toString() {
        String resultado = "";
        resultado += "\n";
        resultado += "======== PEDIDO " + id + " ========\n";
        resultado += "Fecha de pedido: " + fechaPedido + "\n";
        resultado += "Fecha de entrega: " + fechaEntregaEstimada + "\n";
        resultado += "Estado: " + devuelveEstado(estado) + "\n";
        resultado += "Comentario: " + comentario + "\n";
        resultado += "Productos: \n" + pintaProductos(productos) + "\n";
        return resultado;
    }

    /*//Metodo que pinta los productos de un pedido
    private String pintaProductos(ArrayList<Producto> productos) {
        String resultado = "";
        for (Producto p : productos) {
            resultado += p.pintaProductoADetalle() + "\n";
        }
        return resultado;
    }*/

    //Metodo que pinta los productos de un pedido
    public String pintaProductos(List<Producto> productos) {
        String resultado = "";
        Map<Integer, Integer> contador = new HashMap<>();

        for (Producto p : productos) {
            contador.put(p.getId(), contador.getOrDefault(p.getId(), 0) + 1);
        }

        Set<Integer> productosImpresos = new HashSet<>();
        for (Producto p : productos) {
            if (!productosImpresos.contains(p.getId())) {
                int cantidad = contador.get(p.getId());
                resultado += "\t- " + p.getMarca() + " - " + p.getModelo() + " (" + p.getPrecio() + ") x" + cantidad + "\n";
                productosImpresos.add(p.getId());
            }
        }
        return resultado;
    }


    //Metodo que devuelve el estado según el número
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

    //Metodo que cambia el estado del pedido
    public boolean cambiaEstado(int nuevoEstado) {
        if (!(nuevoEstado >= 1 && nuevoEstado <= 4)) return false;
        estado = nuevoEstado;
        return true;
    }

    //Metodo para cambiar la fecha de entrega deliveryDate
    public boolean cambiaFechaEntrega(LocalDate nuevaFecha) {
        if (nuevaFecha.isBefore(fechaPedido)) return false;
        fechaPedido = nuevaFecha;
        return true;
    }

    //Metodo que calcula el total del pedido SIN IVA
    public float calculaTotalPedidoSinIVA() {
        float precioTotalPedido = 0;
        for (Producto p : productos) {
            precioTotalPedido += p.getPrecio();
        }
        return precioTotalPedido;
    }

    //Metodo que calcula el precio del IVA del pedido
    public float calculaIVAPedido(int IVA) {
        float precioIVAPedido = 0;
        for (Producto p : productos) {
            precioIVAPedido += p.getPrecio() * (IVA / 100f);
        }
        return precioIVAPedido;
    }

    //Metodo que calcula el total del pedido
    public float calculaTotalPedidoConIVA(int IVA) {
        return calculaTotalPedidoSinIVA() + calculaIVAPedido(IVA);
    }

    //Metodo que devuelve el número de articulos en un pedido
    public int numArticulos() {
        if (productos == null) return 0;
        return productos.size();
    }

    //Metodo que busca un producto en el pedido por ID
    public Producto buscaProducto(int idProducto) {
        for (Producto p : productos) {
            if (p.getId() == idProducto) return p;
        }
        return null;
    }

    //Metodo para agregar un producto al pedido
    public void addProducto(Producto p) {
        if (productos != null) productos.add(p);
    }
}
