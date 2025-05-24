package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Trabajador implements Serializable {

    //Atributos
    private int id;
    private String nombre;
    private String pass;
    private String email;
    private int movil;
    private ArrayList<Pedido> pedidosAsignados;

    //Constructor
    public Trabajador(int idGenerada, String nombre, String pass, String email, int movil) {
        this.id = idGenerada;
        this.nombre = nombre;
        this.pass = pass;
        this.email = email;
        this.movil = movil;
        this.pedidosAsignados = new ArrayList<>();
    }

    // Constructor copia
    public Trabajador(Trabajador otroTrabajador) {
        this.id = otroTrabajador.id;
        this.nombre = otroTrabajador.nombre;
        this.pass = otroTrabajador.pass;
        this.email = otroTrabajador.email;
        this.movil = otroTrabajador.movil;

        // Hacemos una copia de los pedidos asignados
        this.pedidosAsignados = new ArrayList<>();
        for (Pedido pedido : otroTrabajador.pedidosAsignados) {
            this.pedidosAsignados.add(new Pedido(pedido));
        }
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public ArrayList<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void setPedidosAsignados(ArrayList<Pedido> pedidosAsignados) {
        this.pedidosAsignados = pedidosAsignados;
    }

    //Otros metodos

    //Metodo para el login del Cliente
    public boolean login(String email, String pass) {
        if (email == null || pass == null) return false;
        return (email.equalsIgnoreCase(this.email) && pass.equals(this.pass));
    }

    //Metodo que busca en los pedidos asignados pendientes un pedido por ID
    public Pedido buscaPedidoAsignadoPendiente(int idPedido) {
        ArrayList<Pedido> pedidosAsignadosPendientes = getPedidosPendientes();
        for (Pedido p : pedidosAsignadosPendientes) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que busca en los pedidos asignados completados un pedido por ID
    public Pedido buscaPedidoAsignadoCompletado(int idPedido) {
        ArrayList<Pedido> pedidosAsignadosCompletados = getPedidosCompletados();
        for (Pedido p : pedidosAsignadosCompletados) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que agrega un pedido a los pedidosAsignados
    public boolean asignaPedido(Pedido p) {
        if (p == null || pedidosAsignados.contains(p)) return false;
        pedidosAsignados.add(p);
        return true;
    }

    //Metodo que agrega los pedidos EN PREPARACION a un Array (según el estado = 1 En preparacion) y lo devuelve
    public ArrayList<Pedido> getPedidosPendientes() {
        ArrayList<Pedido> pedidosAsignadosPendientes = new ArrayList<>();
        for (Pedido p : pedidosAsignados) {
            if (p.getEstado() == 0 || p.getEstado() == 1 || p.getEstado() == 2) pedidosAsignadosPendientes.add(p);
        }
        return pedidosAsignadosPendientes;
    }

    //Metodo que agrega los pedidos ENVIADO a un Array (según el estado = 2 ENVIADO) y lo devuelve
    public ArrayList<Pedido> getPedidosCompletados() {
        ArrayList<Pedido> pedidosAsignadosCompletados = new ArrayList<>();
        for (Pedido p : pedidosAsignados) {
            if (p.getEstado() == 2) pedidosAsignadosCompletados.add(p);
        }
        return pedidosAsignadosCompletados;
    }

    //Metodo que devuelve el número de pedidos pendientes
    public int numPedidosPendientes() {
        return getPedidosPendientes().size();
    }

    @Override
    public String toString() {
        String resultado = "";
        resultado += "==================== TRABAJADOR ====================\n";
        resultado += "Nombre: " + nombre + "\n";
        resultado += "Email: " + email + "\n";
        resultado += "Teléfono móvil: " + movil + "\n";
        resultado += "Clave: ";
        String asteriscosClave = "";
        for (int i = 0; i < pass.length(); i++) {
            asteriscosClave += "*";
        }
        resultado += asteriscosClave + "\n";
        return resultado;
    }
}
