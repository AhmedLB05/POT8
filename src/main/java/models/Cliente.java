package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {

    //Atributos
    private int id;
    private String email;
    private String clave;
    private String nombre;
    private String localidad;
    private String provincia;
    private String direccion;
    private int movil;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Producto> carro;

    //Constructor
    public Cliente(int idGenerada, String email, String clave, String nombre, String localidad, String provincia, String direccion, int movil) {
        this.id = idGenerada;
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
        this.localidad = localidad;
        this.provincia = provincia;
        this.direccion = direccion;
        this.movil = movil;
        pedidos = new ArrayList<>();
        carro = new ArrayList<>();
    }

    // Constructor con copia profunda
    public Cliente(Cliente otroCliente) {
        this.id = otroCliente.id;
        this.email = otroCliente.email;
        this.clave = otroCliente.clave;
        this.nombre = otroCliente.nombre;
        this.localidad = otroCliente.localidad;
        this.provincia = otroCliente.provincia;
        this.direccion = otroCliente.direccion;
        this.movil = otroCliente.movil;
        // Copiamos los pedidos
        this.pedidos = new ArrayList<>();
        for (Pedido pedido : otroCliente.pedidos) {
            this.pedidos.add(new Pedido(pedido));
        }
        // Copiamos los productos
        this.carro = new ArrayList<>();
        for (Producto producto : otroCliente.carro) {
            this.carro.add(new Producto(producto));
        }
    }


    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public ArrayList<Producto> getCarro() {
        return carro;
    }

    public void setCarro(ArrayList<Producto> carro) {
        this.carro = carro;
    }

    //Metodo para pintar los datos personales del cliente
    @Override
    public String toString() {
        String resultado = "";

        resultado = " - Nombre: " + nombre + "\n";
        //resultado += " - ID: " + id + "\n";
        resultado += " - Localidad: " + localidad + " - Provincia: " + provincia + "\n";
        resultado += " - Dirección: " + direccion + "\n";
        resultado += " - Telefono de contacto: " + movil + "\n";
        resultado += " - Email: " + email + "\n";
        resultado += " - Clave: ";
        String asteriscosClave = "";
        for (int i = 0; i < clave.length(); i++) {
            asteriscosClave += "*";
        }
        resultado += asteriscosClave + "\n";

        return resultado;
    }

    //Otros metodos

    //Metodo para el login del cliente
    public boolean login(String email, String clave) {
        if (email == null || clave == null) return false;
        return (email.equalsIgnoreCase(this.email) && clave.equals(this.clave));
    }

    //Metodo para agregar un producto al carro
    public void addProductoCarro(Producto p) {
        carro.add(p);
    }

    //Metodo para quitar un producto por ID
    public boolean quitaProductoCarro(int idProducto) {
        for (Producto p : carro) {
            if (p.getId() == idProducto) {
                carro.remove(p);
                return true;
            }
        }
        return false;
    }

    //Metodo que devuelve la cantidad de productos en el carro
    public int numProductosCarro() {
        return carro.size();
    }

    //Metodo para vaciar el carro
    public void vaciaCarro() {
        carro.clear();
    }

    //Metodo para agregar un pedido a los pedidos del cliente
    public void addPedido(Pedido p) {
        pedidos.add(p);
    }

    //Metodo que calcula el precio del carro sin IVA
    public float precioCarroSinIVA() {
        float precioCarroSinIva = 0;
        for (Producto p : carro) {
            precioCarroSinIva += p.getPrecio();
        }
        return precioCarroSinIva;
    }

    //Metodo que calcula el IVA total del carro
    public float precioIVACarro(int IVA) {
        float precioIVACarro = 0;
        for (Producto p : carro) {
            precioIVACarro += p.getPrecio() * (IVA / 100f);
        }
        return precioIVACarro;
    }

    //Metodo que calcula el precio del carro CON IVA
    public float precioCarroConIVA(int IVA) {
        return (precioCarroSinIVA() + precioIVACarro(IVA));
    }

    //Metodo para ver si existe un producto en el carro buscado por ID
    public boolean existeProductoCarro(int idProducto) {
        for (Producto p : carro) {
            if (idProducto == p.getId()) return true;
        }
        return false;
    }


}
