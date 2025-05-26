package controller;

import DAO.*;
import comunications.EnvioMail;
import comunications.EnvioTelegram;
import data.DataProductos;
import models.*;
import persistencia.Persistencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Controlador implements Serializable {

    //Atributos
    private DAOManager dao;
    private DAOAdminSQL daoAdminSQL;
    private DAOCarroSQL daoCarroSQL;
    private DAOClienteSQL daoClienteSQL;
    private DAOPedidoProductosSQL daoPedidoProductosSQL;
    private DAOPedidoSQL daoPedidoSQL;
    private DAOProductoSQL daoProductoSQL;
    private DAOTrabajadorSQL daoTrabajadorSQL;

    //Constructor
    public Controlador() {
        dao = DAOManager.getSinglentonInstance();

        daoAdminSQL = new DAOAdminSQL();
        daoCarroSQL = new DAOCarroSQL();
        daoClienteSQL = new DAOClienteSQL();
        daoPedidoSQL = new DAOPedidoSQL();
        daoPedidoProductosSQL = new DAOPedidoProductosSQL();
        daoProductoSQL = new DAOProductoSQL();
        daoTrabajadorSQL = new DAOTrabajadorSQL();

        ArrayList<Producto> catalogo = DataProductos.getProductosMock();
        if (daoProductoSQL.readAll(dao).isEmpty()) {
            mockCatalogo(catalogo);
        }
        mockAdmin();
    }

    public void mockAdmin() {
        boolean existe = false;
        for (Admin a : daoAdminSQL.readAll(dao)) {
            if (a.getId() == 123456789) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            daoAdminSQL.insert(dao, new Admin(123456789, "admin", "admin", "admin@admin.com"));
        }
    }

    public void mockCatalogo(ArrayList<Producto> catalogo) {
        for (Producto p : catalogo) {
            daoProductoSQL.insert(dao, p);
        }
    }

    public void iniciaDatosCliente() {
        Cliente c1 = new Cliente(123456789, "ahmedlb26205@gmail.com", "123", "Ahmed",
                "Torredelcampo", "Jaén", "Federico Garcia Lorca", 631788372);
        Cliente c2 = new Cliente(987654321, "marcos.lara.0610@fernando3martos.com", "123", "Marcos",
                "Martos", "Jaén", "Calle Ramon Garay", 672929324);
        daoClienteSQL.insert(dao, c1);
        daoClienteSQL.insert(dao, c2);
        //Utils.mensajeGuardadoPersistencia(Persistencia.guardaClientesPersistencia(clientes));
    }

    public void iniciaDatosTrabajadores() {
        Trabajador t1 = new Trabajador(123456789, "Carlos", "123",
                "ahmed.lhaouchi.2602@fernando3martos.com", 672839234);
        Trabajador t2 = new Trabajador(987654321, "Juan", "123", "marcoscano2005@gmail.com",
                672812344);
        daoTrabajadorSQL.insert(dao, t1);
        daoTrabajadorSQL.insert(dao, t2);
        //Utils.mensajeGuardadoPersistencia(Persistencia.guardaTrabajadoresPersistencia(trabajadores));
    }

    //Getters y Setters
    public ArrayList<Cliente> getClientes() {
        return daoClienteSQL.readAll(dao);
    }

    public ArrayList<Trabajador> getTrabajadores() {
        return daoTrabajadorSQL.readAll(dao);
    }

    public ArrayList<Admin> getAdmins() {
        return daoAdminSQL.readAll(dao);
    }

    public ArrayList<Producto> getCatalogo() {
        return daoProductoSQL.readAll(dao);
    }

    //Otros metodos

    //Metodo para el login que devuelve el tipo de objeto
    public Object login(String email, String clave) {
        for (Cliente c : getClientes()) {
            if (c.login(email, clave)) {
                Persistencia.inicioSesionLog(c);
                //guardaUltimoInicioSesion(c); Aqui no funciona porque sino cada vez que iniciemos sesion coge la fecha nueva
                return c;
            }
        }
        for (Trabajador t : getTrabajadores()) {
            if (t.login(email, clave)) {
                Persistencia.inicioSesionLog(t);
                //guardaUltimoInicioSesion(t); Aqui no funciona porque sino cada vez que iniciemos sesion coge la fecha nueva
                return t;
            }
        }
        for (Admin a : getAdmins()) {
            if (a.login(email, clave)) {
                Persistencia.inicioSesionLog(a);
                //guardaUltimoInicioSesion(a); Aqui no funciona porque sino cada vez que iniciemos sesion coge la fecha nueva
                return a;
            }
        }
        return null;
    }

    // Metodo que para guardar el último cierre de sesion de un usuario
    public void guardaUltimoInicioSesion(Object user) {
        Persistencia.guardaCierreSesion(user);
    }

    // Metodo que muestra el ultimo inicio de sesion del usuario
    public String getUltimoInicioSesion(int idUsuario) {
        return Persistencia.ultimoInicioSesion(idUsuario);
    }

    //Metodo que agrega un producto al carro de un cliente que le pasamos
    public boolean addProductoCarrito(Cliente cliente, int idProducto) {
        Producto producto = buscaProductoById(idProducto);
        if (producto == null) return false;
        //cliente.getCarro().add(producto); //ARREGLADO
        return daoCarroSQL.insert(dao, cliente, producto);//TODO
    }

    //Metodo que busca un producto por su id
    public Producto buscaProductoById(int id) {
        for (Producto p : getCatalogo()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    //Metodo para confirmar el pedido de un cliente y asignarlo a un trabajador
    public boolean confirmaPedidoCliente(int id) {
        Cliente clienteTemp = buscaClienteById(id);
        if (clienteTemp == null) {
            System.out.println(" * ERROR NO SE HA ENCONTRADO AL CLIENTE");
        } else {
            if (clienteTemp.getCarro().isEmpty()) return false;

            ArrayList<Producto> copiaCarro = new ArrayList<>();
            copiaCarro.addAll(clienteTemp.getCarro());

            Pedido pedidoTemp = new Pedido(generaIdPedido(), LocalDate.now(), "Pedido creado", copiaCarro);

            //clienteTemp.addPedido(pedidoTemp);
            daoPedidoSQL.insert(dao, pedidoTemp, clienteTemp);
            daoPedidoProductosSQL.insert(dao, pedidoTemp);
            daoClienteSQL.update(dao, clienteTemp);
            Persistencia.guardaResumenPedido(pedidoTemp);
            EnvioMail.enviaCorreoResumen(clienteTemp.getEmail(), pedidoTemp);
            clienteTemp.vaciaCarro();
            daoCarroSQL.deleteAll(dao, clienteTemp);
            Trabajador trabajadorTemp = buscaTrabajadorCandidatoParaAsignar();

            if (trabajadorTemp != null) {
                if (asignaPedido(pedidoTemp.getId(), trabajadorTemp.getId())) {
                    PedidoClienteDataClass dataTemp = null;

                    for (PedidoClienteDataClass p : getPedidosAsignadosTrabajador(trabajadorTemp.getId())) {
                        if (p.getIdPedido() == pedidoTemp.getId()) dataTemp = p;
                    }
                    daoPedidoSQL.updateTrabajador(dao, pedidoTemp, trabajadorTemp);
                    EnvioMail.enviaCorreoPedido(trabajadorTemp, dataTemp, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                    EnvioTelegram.enviaMensajeTrabajadorPedidoAsignado(trabajadorTemp, pedidoTemp);
                }
            }
        }
        return true;
    }

    //Metodo que busca un trabajador con menos pedidos para asignarle uno nuevo
    public Trabajador buscaTrabajadorCandidatoParaAsignar() {
        int numPedidosMin = Integer.MAX_VALUE;
        Trabajador candidato = null;

        for (Trabajador t : getTrabajadores()) {
            if (t.numPedidosPendientes() < numPedidosMin) {
                numPedidosMin = t.numPedidosPendientes();
                candidato = t;
            }
        }
        if (getTrabajadores().size() > 1) if (hayEmpateTrabajadoresCandidatos(candidato)) return null;
        return candidato;
    }

    // Metodo que te comprueba si tienes
    public boolean accesoInvitado() {
        return Persistencia.getAccesoInvitados();
    }


    //Metodo que nos indica si hay algún trabajador empatado en cuanto a pedidos pendientes con el trabajador que le pasamos
    public boolean hayEmpateTrabajadoresCandidatos(Trabajador candidato) {
        for (Trabajador t : getTrabajadores()) {
            if (t.getId() != candidato.getId())
                if (t.getPedidosPendientes().size() == candidato.getPedidosPendientes().size()) return true;
        }
        return false;
    }

    //Metodo que busca a un cliente por su ID
    public Cliente buscaClienteById(int idCliente) {
        for (Cliente c : getClientes()) {
            if (c.getId() == idCliente) return c;
        }
        return null;
    }

    //Metodo que busca a un cliente por su ID (metodo inventado por Ahmed)
    public Object buscaClienteByEmail(String emailIntro) {
        for (Cliente c : getClientes()) {
            if (c.getEmail().equalsIgnoreCase(emailIntro)) return c;
        }
        return null;
    }

    //Metodo que agrega un cliente al sistema (ArrayList) (metodo inventado por Ahmed)
    public boolean agregaClienteSistema(Cliente c) {
        /*if (daoClienteSQL.insert(dao, c)) {
            Persistencia.guardaClientesPersistencia(getClientes());
            return true;
        }
        return false;*/
        return daoClienteSQL.insert(dao, c);
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre de la marca
    public ArrayList<Producto> buscaProductosByMarca(String marca) {
        ArrayList<Producto> productosCoincideMarca = new ArrayList<>();
        for (Producto p : getCatalogo()) {
            if (p.getMarca().toLowerCase().contains(marca.toLowerCase())) productosCoincideMarca.add(p);
        }
        return productosCoincideMarca;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre del modelo
    public ArrayList<Producto> buscaProductosByModelo(String modelo) {
        ArrayList<Producto> productosCoincideModelo = new ArrayList<>();
        for (Producto p : getCatalogo()) {
            if (p.getModelo().toLowerCase().contains(modelo.toLowerCase())) productosCoincideModelo.add(p);
        }
        return productosCoincideModelo;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en la descripcion
    public ArrayList<Producto> buscaProductosByDescripcion(String descripcion) {
        ArrayList<Producto> productosCoincideDescripcion = new ArrayList<>();
        for (Producto p : getCatalogo()) {
            if (p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                productosCoincideDescripcion.add(p);
        }
        return productosCoincideDescripcion;
    }

    //Metodo que busca en el catálogo producto un término que se encuentre en descripcion o marca o modelo
    public ArrayList<Producto> buscaProductosByTermino(String termino) {
        ArrayList<Producto> productosCoincideTermino = new ArrayList<>();
        String terminoLower = termino.toLowerCase();

        for (Producto p : getCatalogo()) {
            if ((p.getDescripcion().toLowerCase().contains(terminoLower) ||
                    p.getMarca().toLowerCase().contains(terminoLower) ||
                    p.getModelo().toLowerCase().contains(terminoLower)) &&
                    !productosCoincideTermino.contains(p)) productosCoincideTermino.add(p);
        }
        return productosCoincideTermino;
    }


    //Metodo que busca en el catálogo producto que esten entre un rango de precios
    public ArrayList<Producto> buscaProductosByPrecio(float precioMin, float precioMax) {
        ArrayList<Producto> productosCoincidePrecio = new ArrayList<>();
        for (Producto p : getCatalogo()) {
            if (p != null && p.getPrecio() <= precioMax && p.getPrecio() >= precioMin) productosCoincidePrecio.add(p);
        }
        return productosCoincidePrecio;
    }

    public boolean editarProducto(Producto p) {
        for (Producto producto : getCatalogo()) {
            if (producto.getId() == p.getId()) {
                producto.setMarca(p.getMarca());
                producto.setModelo(p.getModelo());
                producto.setDescripcion(p.getDescripcion());
                producto.setPrecio(p.getPrecio());
                return daoProductoSQL.update(dao, producto);
            }
        }
        return false;
    }

    //Metodo que devuelve todos los pedidos existentes
    public ArrayList<Pedido> getTodosPedidos() {
        ArrayList<Pedido> todosPedidos = new ArrayList<>();
        for (Cliente c : getClientes()) {
            if (!c.getPedidos().isEmpty()) todosPedidos.addAll(c.getPedidos());
        }
        return todosPedidos;
    }

    //Metodo para ver la cantidad de pedidos en el sistema
    public int numPedidosTotales() {
        int cont = 0;
        for (Cliente c : getClientes()) {
            if (c.getPedidos() != null) cont += c.getPedidos().size();
        }
        return cont;
    }

    //Metodo que busca un pedido por su ID
    public Pedido buscaPedidoById(int idPedido) {
        for (Pedido p : getTodosPedidos()) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que cambia el estado de un pedido
    public boolean cambiaEstadoPedido(int idPedido, int nuevoEstado) {
        for (Pedido p : getTodosPedidos()) {
            if (p.getId() == idPedido) {
                p.setEstado(nuevoEstado);
                daoPedidoSQL.update(dao, p);
                Persistencia.guardaActualizaPedido(p);
                Persistencia.guardaClientesPersistencia(getClientes());
                Persistencia.guardaTrabajadoresPersistencia(getTrabajadores());
                Persistencia.guardaAdminsPersistencia(getAdmins());
                return true;
            }
        }
        return false;
    }

    //Metodo que crea un trabajador y lo añade al array de trabajadores
    public boolean nuevoTrabajador(String email, String clave, String nombre, int movil) { //TODO
        Trabajador nuevoTrabajador = new Trabajador(generaIdTrabajador(), nombre, clave, email, movil);
        daoTrabajadorSQL.insert(dao, nuevoTrabajador);

        ArrayList<Pedido> pedidosSinAsignar = pedidosSinTrabajador();
        Trabajador candidato = buscaTrabajadorCandidatoParaAsignar();

        if (!pedidosSinAsignar.isEmpty() && candidato != null) {
            for (Pedido p : pedidosSinAsignar) {
                asignaPedido(p.getId(), candidato.getId());
            }

            for (Pedido pedido : getTodosPedidos()) {
                for (PedidoClienteDataClass pDataClass : getPedidosAsignadosTrabajador(candidato.getId())) {
                    if (pedido.getId() == pDataClass.getIdPedido())
                        EnvioMail.enviaCorreoPedido(candidato, pDataClass, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                }
            }
        }
        return true;
    }

    //Metodo que devuelve el trabajador al que está asignado un pedido
    public Trabajador buscaTrabajadorAsignadoAPedido(int idPedido) {
        for (Trabajador t : getTrabajadores()) {
            for (Pedido p : t.getPedidosAsignados()) {
                if (p.getId() == idPedido) return t;
            }
        }
        return null;
    }

    //Metodo que nos devuelve los pedidos sin asignar recorre todos los trabajadores recabando los pedidos asignados
    //luego recorre los clientes pillando los pedidos y luego de la lista de pedidosClientes le quita los pedidos ya asignados a trabajadores
    public ArrayList<Pedido> pedidosSinTrabajador() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Trabajador> trabajadores = getTrabajadores();
        ArrayList<Cliente> clientes = getClientes();

        if (!trabajadores.isEmpty()) {
            for (Cliente c : clientes) {
                for (Pedido p : c.getPedidos()) {
                    if (buscaTrabajadorAsignadoAPedido(p.getId()) == null) pedidos.add(p);
                }
            }
        } else {
            for (Cliente c : clientes) {
                pedidos.addAll(c.getPedidos());
            }
        }
        return pedidos;
    }

    //Metodo para asignar un pedido
    public boolean asignaPedido(int idPedido, int idTrabajador) {
        Pedido pedidoTemp = buscaPedidoById(idPedido);
        Trabajador trabajadorTemp = buscaTrabajadorByID(idTrabajador);

        if (pedidoTemp == null) return false;
        if (trabajadorTemp == null) return false;

        boolean flag = trabajadorTemp.asignaPedido(pedidoTemp);

        if (flag) {
            Cliente cliente = sacaClienteDeUnPedido(pedidoTemp.getId());
            if (cliente != null) {
                //Persistencia.guardaNuevoPedido(cliente.getId(), trabajadorTemp.getId());
                daoPedidoSQL.updateTrabajador(dao, pedidoTemp, trabajadorTemp);
            }
            //Persistencia.guardaTrabajadoresPersistencia(trabajadores);
            //Persistencia.guardaClientesPersistencia(clientes);
        }
        return flag;
    }

    //Metodo que devuelve el cliente que ha hecho un pedido
    private Cliente sacaClienteDeUnPedido(int idPedido) {
        for (Cliente c : getClientes()) {
            if (c != null && !c.getPedidos().isEmpty()) {
                for (Pedido p : c.getPedidos()) {
                    if (p.getId() == idPedido) return c;
                }
            }
        }
        return null;
    }

    //Metodo que busca el trabajador por ID
    public Trabajador buscaTrabajadorByID(int idTrabajador) {
        for (Trabajador t : getTrabajadores()) {
            if (t.getId() == idTrabajador) return t;
        }
        return null;
    }

    //Metodo que busca un pedido en los pedidos asignados de un trabajador
    public Pedido buscaPedidoAsignadoTrabajador(int idTrabajador, int idPedido) {
        Trabajador t = buscaTrabajadorByID(idTrabajador);
        if (t == null || t.getPedidosAsignados() == null) return null;
        for (Pedido p : t.getPedidosAsignados()) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    //Metodo que busca los pedido asignados en el trabajador
    public ArrayList<PedidoClienteDataClass> getPedidosAsignadosTrabajador(int idTrabajador) {//TODO
        ArrayList<PedidoClienteDataClass> pedidosAsignadosT = new ArrayList<>();
        Trabajador temp = buscaTrabajadorByID(idTrabajador);
        ArrayList<Cliente> clientes = getClientes();

        for (Pedido pT : temp.getPedidosAsignados()) {
            for (Cliente c : clientes) {
                for (Pedido pA : c.getPedidos()) {
                    if (pA.getId() == pT.getId()) {
                        pedidosAsignadosT.add(new PedidoClienteDataClass(c.getId(), c.getEmail(), c.getNombre(), c.getLocalidad(), c.getProvincia(), c.getDireccion(), c.getMovil(), pA.getId(), pA.getFechaPedido(), pA.getFechaEntregaEstimada(), pA.getEstado(), pA.getComentario(), pA.getProductos()));
                    }
                }
            }
        }
        return pedidosAsignadosT;
    }

    //Metodo que devuelve un array de pedidos completados
    public ArrayList<PedidoClienteDataClass> getPedidosCompletadosTrabajador(int idTrabajador) {
        ArrayList<PedidoClienteDataClass> pedidosCompletadosT = new ArrayList<>();
        Trabajador temp = buscaTrabajadorByID(idTrabajador);

        //Bucle que mira los pedidos completados de los trabajadores
        for (Pedido pT : temp.getPedidosCompletados()) {
            // Bucle del cliente
            for (Cliente c : getClientes()) {
                // Bucle que mira los pedidos de los clientes
                for (Pedido pC : c.getPedidos()) {
                    if (pC.getId() == pT.getId()) {
                        pedidosCompletadosT.add(new PedidoClienteDataClass(c.getId(), c.getEmail(), c.getNombre(), c.getLocalidad(), c.getProvincia(), c.getDireccion(), c.getMovil(), pC.getId(), pC.getFechaPedido(), pC.getFechaEntregaEstimada(), pC.getEstado(), pC.getComentario(), pC.getProductos()));
                        break;
                    }
                }
            }
        }
        return pedidosCompletadosT;
    }

    //Metodo que pasa los pedidos asignados y completados al trabajador
    public ArrayList<PedidoClienteDataClass> getPedidosAsignadosYCompletados(int idTrabajador) {
        ArrayList<PedidoClienteDataClass> pedidos = new ArrayList<>();
        Trabajador temp = buscaTrabajadorByID(idTrabajador);

        pedidos.addAll(getPedidosAsignadosTrabajador(temp.getId()));
        pedidos.addAll(getPedidosCompletadosTrabajador(temp.getId()));
        return pedidos;
    }

    //Metodo que genera el id del cliente SI EMPIEZA POR 2 ES CLIENTE
    private int generaIdCliente() {
        int idCliente;
        do {
            idCliente = (int) ((Math.random() * 90000) + 10000);
        } while (buscaClienteById(idCliente) != null);
        idCliente = Integer.parseInt(("2" + idCliente));
        return idCliente;
    }

    //Metodo que genera el id del producto
    private int generaIdProducto() {
        int idProducto;
        do {
            idProducto = (int) ((Math.random() * 900000) + 100000);
        } while (buscaProductoById(idProducto) != null);
        idProducto = Integer.parseInt(("9" + idProducto));
        return idProducto;
    }

    //Metodo que genera el id del pedido
    private int generaIdPedido() {
        int idPedido;
        do {
            idPedido = (int) ((Math.random() * 900000) + 100000);
        } while (buscaPedidoById(idPedido) != null);
        idPedido = Integer.parseInt(("9" + idPedido));
        return idPedido;
    }

    //Metodo que genera el id del admin SI EMPIEZA POR 3 ES ADMIN
    private int generaIdAdmin() {
        int idAdmin;
        do {
            idAdmin = (int) ((Math.random() * 90000) + 10000);
        } while (buscaAdminById(idAdmin) != null);
        idAdmin = Integer.parseInt(("3" + idAdmin));
        return idAdmin;
    }

    //Metodo para buscar un admin por su id aunque solo haya 1
    private Admin buscaAdminById(int idAdmin) {
        for (Admin a : getAdmins()) {
            if (a.getId() == idAdmin) return a;
        }
        return null;
    }

    //Metodo que genera el id del trabajador SI EMPIEZA POR 1 ES TRABAJADOR
    private int generaIdTrabajador() {
        int idTrabajador;
        do {
            idTrabajador = (int) ((Math.random() * 90000) + 10000);
        } while (buscaTrabajadorByID(idTrabajador) != null);
        idTrabajador = Integer.parseInt(("1" + idTrabajador));
        return idTrabajador;
    }

    //Metodo que devuelve el total de pedidos pendientes de entrega a un cliente (se hace mirando el estado)
    public int getTotalPedidosPendientesEntregaCliente(Cliente cliente) {
        int cont = 0;
        for (Pedido p : cliente.getPedidos()) {
            if (p.getEstado() != 3 && p.getEstado() != 4) cont++;
        }
        return cont;
    }

    //Metodo que se utiliza al cambiar los datos personales de un cliente
    public boolean actualizaDatosCliente(Cliente cliente) {
        for (Cliente c : getClientes()) {
            if (c.getId() == cliente.getId()) {
                return daoClienteSQL.update(dao, cliente);
            }
        }
        return false;
    }

    //Metodo para el registro de clientes
    public boolean registraCliente(String emailIntro, String claveIntro, String nombreIntro, String localidadIntro, String provinciaIntro, String direccionIntro, int movilIntro) {
        Cliente cliente = new Cliente(generaIdCliente(), emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro);
        return daoClienteSQL.insert(dao, cliente);
    }

    //Metodo que se utiliza al cambiar los datos personales de un trabajador
    public boolean actualizaDatosTrabajador(Trabajador trabajadorCambiaDatos) {
        for (Trabajador t : getTrabajadores()) {
            if (t.getId() == trabajadorCambiaDatos.getId()) {
                daoTrabajadorSQL.update(dao, trabajadorCambiaDatos);
                return true;
            }
        }
        return false;
    }

    //Metodo que busca un trabajador por su email
    public Trabajador buscaTrabajadorByEmail(String email) {
        for (Trabajador t : getTrabajadores()) {
            if (t.getEmail().equalsIgnoreCase(email)) return t;
        }
        return null;
    }

    //Metodo que devuelve la cantidad de pedidos pendientes de todos los trabajadores
    public int numPedidosPendientes() {
        int cont = 0;
        for (Trabajador t : getTrabajadores()) {
            if (!t.getPedidosPendientes().isEmpty()) cont += t.getPedidosPendientes().size();
        }
        return cont;
    }

    //Metodo que devuelve la cantidad de pedidos completados o cancelados de todos los trabajadores
    public int numPedidosCompletadosCancelados() {
        int cont = 0;
        for (Trabajador t : getTrabajadores()) {
            if (!t.getPedidosCompletados().isEmpty()) cont += t.getPedidosCompletados().size();
        }
        return cont;
    }

    //Metodo que devuelve todos los pedidos pero con el modelo PedidoClienteDataClass
    public ArrayList<PedidoClienteDataClass> getTodosPedidosClienteDataClass() {
        ArrayList<PedidoClienteDataClass> todosPedidosCliente = new ArrayList<>();
        for (Cliente c : getClientes()) {
            if (!c.getPedidos().isEmpty()) for (Pedido p : c.getPedidos()) {
                todosPedidosCliente.add(new PedidoClienteDataClass(c, p));
            }
        }
        return todosPedidosCliente;
    }

    //Metodo que cancela un pedido de un cliente
    public boolean cancelaPedidoCliente(int idCliente, int idPedido) {
        Cliente clienteTemp = buscaClienteById(idCliente);
        Pedido pedidoTemp = buscaPedidoById(idPedido);

        if (clienteTemp == null) return false;
        if (pedidoTemp == null) return false;

        return pedidoTemp.cambiaEstado(4);
    }

    public boolean guardaClientes() {
        return Persistencia.guardaClientesPersistencia(getClientes());
    }

    public boolean guardaTrabajadores() {
        return Persistencia.guardaTrabajadoresPersistencia(getTrabajadores());
    }

    public boolean guardaAdmin() {
        return Persistencia.guardaAdminsPersistencia(getAdmins());
    }

    public boolean guardaCatalogo() {
        return Persistencia.guardaProductosPersistencia(getCatalogo());
    }

    //Metodo porque cuando solo quedaba un trabajador y se eliminaba daba una excepcion
    public boolean bajaTrabajador(Trabajador trabajadorBaja) {
        Iterator<Trabajador> it = getTrabajadores().iterator();
        while (it.hasNext()) {
            Trabajador t = it.next();
            if (t.equals(trabajadorBaja)) {
                Persistencia.eliminaTrabajador(t);
                daoTrabajadorSQL.delete(dao, t);
                it.remove();
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> configProperties() {
        return Persistencia.configuracionPrograma();
    }

    public boolean creaBackup() {
        return Persistencia.creaBackup(this);
    }


    public boolean creaBackupPersonalizado(String ruta) {
        return Persistencia.creaBackupPersonalizado(ruta, this);
    }

    /*public boolean recuperaBackup() {
        Controlador backupControlador = Persistencia.recuperaBackup();
        if (backupControlador == null) return false;
        clientes = backupControlador.clientes;
        trabajadores = backupControlador.trabajadores;
        admins = backupControlador.admins;
        catalogo = backupControlador.catalogo;
        return true;
    }

    public boolean recuperaBackupPersonalizado(String ruta) {
        Controlador backupControlador = Persistencia.recuperaBackupPersonalizado(ruta);
        if (backupControlador == null) return false;
        clientes = backupControlador.clientes;
        trabajadores = backupControlador.trabajadores;
        admins = backupControlador.admins;
        catalogo = backupControlador.catalogo;
        return true;
    }*/

    public void adjuntaCorreosExcel(String email) {
        ArrayList<Pedido> listado = getTodosPedidos();
        Persistencia.adjuntaCorreos(listado);
        EnvioMail.enviarExcelCorreo(email);
    }

    public void actualizaComentario(PedidoClienteDataClass p, String comentarioNuevo) {
        for (Pedido pedido : getTodosPedidos()) {
            if (p.getIdPedido() == pedido.getId()) {
                pedido.setComentario(comentarioNuevo);
                daoPedidoSQL.update(dao, pedido);
                Persistencia.guardaActualizaPedido(pedido);
            }
        }
        Persistencia.guardaAdminsPersistencia(getAdmins());
        Persistencia.guardaClientesPersistencia(getClientes());
        Persistencia.guardaTrabajadoresPersistencia(getTrabajadores());
    }

    public Admin buscaAdminByID(int id) {
        for (Admin a : getAdmins()) {
            if (a.getId() == id) return a;
        }
        return null;
    }
}
