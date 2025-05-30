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

public class Controlador implements Serializable {

    //Atributos
    private final DAOManager dao;
    private final DAOAdminSQL daoAdminSQL;
    private final DAOCarroSQL daoCarroSQL;
    private final DAOClienteSQL daoClienteSQL;
    private final DAOPedidoProductosSQL daoPedidoProductosSQL;
    private final DAOPedidoSQL daoPedidoSQL;
    private final DAOProductoSQL daoProductoSQL;
    private final DAOTrabajadorSQL daoTrabajadorSQL;

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
        ArrayList<Admin> admins = daoAdminSQL.readAll(dao);
        for (Admin a : admins) {
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
        Cliente c1 = new Cliente(123456789, "ahmedlb26205@gmail.com", "123", "Ahmed", "Torredelcampo", "Jaén", "Federico Garcia Lorca", 631788372);
        Cliente c2 = new Cliente(987654321, "marcos.lara.0610@fernando3martos.com", "123", "Marcos", "Martos", "Jaén", "Calle Ramon Garay", 672929324);
        daoClienteSQL.insert(dao, c1);
        daoClienteSQL.insert(dao, c2);
        //Utils.mensajeGuardadoPersistencia(Persistencia.guardaClientesPersistencia(clientes));
    }

    public void iniciaDatosTrabajadores() {
        Trabajador t1 = new Trabajador(123456789, "Carlos", "123", "ahmed.lhaouchi.2602@fernando3martos.com", 672839234);
        Trabajador t2 = new Trabajador(987654321, "Juan", "123", "marcoscano2005@gmail.com", 672812344);
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
        ArrayList<Cliente> clientes = getClientes();
        ArrayList<Trabajador> trabajadores = getTrabajadores();
        ArrayList<Admin> admins = getAdmins();
        for (Cliente c : clientes) {
            if (c.login(email, clave)) {
                Persistencia.inicioSesionLog(c);
                //guardaUltimoInicioSesion(c); Aqui no funciona porque sino cada vez que iniciemos sesion coge la fecha nueva
                return c;
            }
        }
        for (Trabajador t : trabajadores) {
            if (t.login(email, clave)) {
                Persistencia.inicioSesionLog(t);
                //guardaUltimoInicioSesion(t); Aqui no funciona porque sino cada vez que iniciemos sesion coge la fecha nueva
                return t;
            }
        }
        for (Admin a : admins) {
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
        return daoCarroSQL.insert(dao, cliente, producto);
    }

    //Metodo que busca un producto por su id
    public Producto buscaProductoById(int id) {
        ArrayList<Producto> catalogo = getCatalogo();
        for (Producto p : catalogo) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    //Metodo para confirmar el pedido de un cliente y asignarlo a un trabajador
    public boolean confirmaPedidoCliente(int id) {
        Cliente clienteTemp = buscaClienteById(id);
        if (clienteTemp == null) {
            System.out.println(" * ERROR NO SE HA ENCONTRADO AL CLIENTE");
            return false;
        }

        if (clienteTemp.getCarro().isEmpty()) return false;

        ArrayList<Producto> copiaCarro = new ArrayList<>();
        copiaCarro.addAll(clienteTemp.getCarro());

        Pedido pedidoTemp = new Pedido(generaIdPedido(), LocalDate.now(), "Pedido creado", copiaCarro);

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
                ArrayList<PedidoClienteDataClass> pedidosAsignados = getPedidosAsignadosTrabajador(trabajadorTemp.getId());
                PedidoClienteDataClass dataTemp = null;
                for (PedidoClienteDataClass p : pedidosAsignados) {
                    if (p.getIdPedido() == pedidoTemp.getId()) {
                        dataTemp = p;
                        break;
                    }
                }
                daoPedidoSQL.updateTrabajador(dao, pedidoTemp, trabajadorTemp);
                EnvioMail.enviaCorreoPedido(trabajadorTemp, dataTemp, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                EnvioTelegram.enviaMensajeTrabajadorPedidoAsignado(trabajadorTemp, pedidoTemp);
            }
        }
        return true;
    }

    //Metodo que busca un trabajador con menos pedidos para asignarle uno nuevo
    public Trabajador buscaTrabajadorCandidatoParaAsignar() {
        ArrayList<Trabajador> trabajadores = getTrabajadores();
        int numPedidosMin = Integer.MAX_VALUE;
        Trabajador candidato = null;
        for (Trabajador t : trabajadores) {
            if (t.numPedidosPendientes() < numPedidosMin) {
                numPedidosMin = t.numPedidosPendientes();
                candidato = t;
            }
        }
        if (trabajadores.size() > 1) if (hayEmpateTrabajadoresCandidatos(candidato, trabajadores)) return null;
        return candidato;
    }

    // Metodo que te comprueba si tienes
    public boolean accesoInvitado() {
        return Persistencia.getAccesoInvitados();
    }


    //Metodo que nos indica si hay algún trabajador empatado en cuanto a pedidos pendientes con el trabajador que le pasamos
    public boolean hayEmpateTrabajadoresCandidatos(Trabajador candidato, ArrayList<Trabajador> trabajadores) {
        for (Trabajador t : trabajadores) {
            if (t.getId() != candidato.getId())
                if (t.getPedidosPendientes().size() == candidato.getPedidosPendientes().size()) return true;
        }
        return false;
    }

    //Metodo que busca a un cliente por su ID
    public Cliente buscaClienteById(int idCliente) {
        ArrayList<Cliente> clientes = getClientes();
        for (Cliente c : clientes) {
            if (c.getId() == idCliente) return c;
        }
        return null;
    }

    //Metodo que busca a un cliente por su ID (metodo inventado por Ahmed)
    public Object buscaClienteByEmail(String emailIntro) {
        ArrayList<Cliente> clientes = getClientes();
        for (Cliente c : clientes) {
            if (c.getEmail().equalsIgnoreCase(emailIntro)) return c;
        }
        return null;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre de la marca
    public ArrayList<Producto> buscaProductosByMarca(String marca, ArrayList<Producto> catalogo) {
        ArrayList<Producto> productosCoincideMarca = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getMarca().toLowerCase().contains(marca.toLowerCase())) productosCoincideMarca.add(p);
        }
        return productosCoincideMarca;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en el nombre del modelo
    public ArrayList<Producto> buscaProductosByModelo(String modelo, ArrayList<Producto> catalogo) {
        ArrayList<Producto> productosCoincideModelo = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getModelo().toLowerCase().contains(modelo.toLowerCase())) productosCoincideModelo.add(p);
        }
        return productosCoincideModelo;
    }

    //Metodo que busca en el catálogo productos que tengan coincidencia en la descripcion
    public ArrayList<Producto> buscaProductosByDescripcion(String descripcion, ArrayList<Producto> catalogo) {
        ArrayList<Producto> productosCoincideDescripcion = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                productosCoincideDescripcion.add(p);
        }
        return productosCoincideDescripcion;
    }

    //Metodo que busca en el catálogo producto un término que se encuentre en descripcion o marca o modelo
    public ArrayList<Producto> buscaProductosByTermino(String termino, ArrayList<Producto> catalogo) {
        ArrayList<Producto> productosCoincideTermino = new ArrayList<>();
        String terminoLower = termino.toLowerCase();

        for (Producto p : catalogo) {
            if ((p.getDescripcion().toLowerCase().contains(terminoLower) || p.getMarca().toLowerCase().contains(terminoLower) || p.getModelo().toLowerCase().contains(terminoLower)) && !productosCoincideTermino.contains(p))
                productosCoincideTermino.add(p);
        }
        return productosCoincideTermino;
    }


    //Metodo que busca en el catálogo producto que esten entre un rango de precios
    public ArrayList<Producto> buscaProductosByPrecio(float precioMin, float precioMax, ArrayList<Producto> catalogo) {
        ArrayList<Producto> productosCoincidePrecio = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p != null && p.getPrecio() <= precioMax && p.getPrecio() >= precioMin) productosCoincidePrecio.add(p);
        }
        return productosCoincidePrecio;
    }

    //Metodo que devuelve todos los pedidos existentes
    public ArrayList<Pedido> getTodosPedidos() {
        return daoPedidoSQL.readAll(dao);
    }

    //Metodo para ver la cantidad de pedidos en el sistema
    public int numPedidosTotales(ArrayList<Cliente> clientes) {
        int cont = 0;
        for (Cliente c : clientes) {
            if (c.getPedidos() != null) cont += c.getPedidos().size();
        }
        return cont;
    }

    //Metodo que busca un pedido por su ID
    public Pedido buscaPedidoById(int idPedido) {
        ArrayList<Pedido> todosPedidos = getTodosPedidos();
        for (Pedido p : todosPedidos) {
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
    public boolean nuevoTrabajador(String email, String clave, String nombre, int movil) {
        ArrayList<Trabajador> trabajadores = getTrabajadores();
        ArrayList<Cliente> clientes = getClientes();

        Trabajador nuevoTrabajador = new Trabajador(generaIdTrabajador(), nombre, clave, email, movil);
        daoTrabajadorSQL.insert(dao, nuevoTrabajador);

        ArrayList<Pedido> pedidosSinAsignar = pedidosSinTrabajador(trabajadores, clientes);
        Trabajador candidato = buscaTrabajadorCandidatoParaAsignar();

        if (!pedidosSinAsignar.isEmpty() && candidato != null) {
            for (Pedido p : pedidosSinAsignar) {
                asignaPedido(p.getId(), candidato.getId());
            }
            ArrayList<Pedido> todosPedidos = getTodosPedidos();
            for (Pedido pedido : todosPedidos) {
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
        ArrayList<Trabajador> trabajadores = getTrabajadores();
        for (Trabajador t : trabajadores) {
            for (Pedido p : t.getPedidosAsignados()) {
                if (p.getId() == idPedido) return t;
            }
        }
        return null;
    }

    //Metodo que nos devuelve los pedidos sin asignar recorre todos los trabajadores recabando los pedidos asignados
    //luego recorre los clientes pillando los pedidos y luego de la lista de pedidosClientes le quita los pedidos ya asignados a trabajadores
    public ArrayList<Pedido> pedidosSinTrabajador(ArrayList<Trabajador> trabajadores, ArrayList<Cliente> clientes) {
        ArrayList<Pedido> pedidos = new ArrayList<>();

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
                Persistencia.guardaNuevoPedido(cliente.getId(), trabajadorTemp.getId());
                daoPedidoSQL.updateTrabajador(dao, pedidoTemp, trabajadorTemp);
            }
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

    //Metodo que genera el id del cliente SI EMPIEZA POR 2 ES CLIENTE
    private int generaIdCliente() {
        int idCliente;
        do {
            idCliente = (int) ((Math.random() * 90000) + 10000);
        } while (buscaClienteById(idCliente) != null);
        idCliente = Integer.parseInt(("2" + idCliente));
        return idCliente;
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
    public int numPedidosPendientes(ArrayList<Trabajador> trabajadores) {
        int cont = 0;
        for (Trabajador t : trabajadores) {
            if (!t.getPedidosPendientes().isEmpty()) cont += t.getPedidosPendientes().size();
        }
        return cont;
    }

    //Metodo que devuelve la cantidad de pedidos completados o cancelados de todos los trabajadores
    public int numPedidosCompletadosCancelados(ArrayList<Trabajador> trabajadores) {
        int cont = 0;
        for (Trabajador t : trabajadores) {
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

        pedidoTemp.cambiaEstado(4);
        return daoPedidoSQL.update(dao, pedidoTemp);
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
        for (Trabajador t : getTrabajadores()) {
            if (t.getId() == trabajadorBaja.getId()) {
                Persistencia.eliminaTrabajador(t);
                return daoTrabajadorSQL.delete(dao, t);
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

    public boolean recuperaBackup() {
        ArrayList<Cliente> clientesBackup = Persistencia.recuperaClientesBackup();
        ArrayList<Trabajador> trabajadoresBackup = Persistencia.recuperaTrabajadoresBackup();
        ArrayList<Admin> adminsBackup = Persistencia.recuperaAdminsBackup();
        ArrayList<Producto> catalogoBackup = Persistencia.recuperaProductosBackup();

        if (clientesBackup == null || trabajadoresBackup == null || adminsBackup == null || catalogoBackup == null)
            return false;

        //Eliminamos los clientes de la base de datos
        for (Cliente c : getClientes()) {
            daoClienteSQL.delete(dao, c);
        }
        //Eliminamos los trabajadores de la base de datos
        for (Trabajador t : getTrabajadores()) {
            daoTrabajadorSQL.delete(dao, t);
        }
        //Eliminamos los admins de la base de datos
        for (Admin a : getAdmins()) {
            daoAdminSQL.delete(dao, a);
        }
        //Eliminamos los productos de la base de datos
        for (Producto p : getCatalogo()) {
            daoProductoSQL.delete(dao, p);
        }

        //Recuperamos los productos del backup
        for (Producto p : catalogoBackup) {
            daoProductoSQL.insert(dao, p);
        }

        //Recuperamos los admins del backup
        for (Admin a : adminsBackup) {
            daoAdminSQL.insert(dao, a);
        }

        //Recuperamos los trabajadores del backup
        for (Trabajador t : trabajadoresBackup) {
            daoTrabajadorSQL.insert(dao, t);
        }

        //Recuperamos los clientes del backup
        for (Cliente c : clientesBackup) {
            daoClienteSQL.insert(dao, c);
            if (!c.pedidosPersistencia().isEmpty()) {
                for (Pedido p : c.pedidosPersistencia()) {
                    daoPedidoSQL.insert(dao, p, c);
                    daoPedidoProductosSQL.insert(dao, p);
                }
            }
            if (!c.carroPersistencia().isEmpty()) {
                for (Producto p : c.carroPersistencia()) {
                    daoCarroSQL.insert(dao, c, p);
                }
            }
        }

        for (Trabajador t : trabajadoresBackup) {
            if (!t.pedidosAsignadosPersistencia().isEmpty()) {
                for (Pedido p : t.pedidosAsignadosPersistencia()) {
                    daoPedidoSQL.updateTrabajador(dao, p, t);
                }
            }
        }
        return true;
    }

    public boolean recuperaBackupPersonalizado(String ruta) {
        ArrayList<Cliente> clientesBackup = Persistencia.recuperaClientesBackupPersonlizado(ruta);
        ArrayList<Trabajador> trabajadoresBackup = Persistencia.recuperaTrabajadoresBackupPersonalizado(ruta);
        ArrayList<Admin> adminsBackup = Persistencia.recuperaAdminsBackupPersonalizado(ruta);
        ArrayList<Producto> catalogoBackup = Persistencia.recuperaProductosBackupPersonalizado(ruta);

        if (clientesBackup == null || trabajadoresBackup == null || adminsBackup == null || catalogoBackup == null)
            return false;

        //Eliminamos los clientes de la base de datos
        for (Cliente c : getClientes()) {
            daoClienteSQL.delete(dao, c);
        }
        //Eliminamos los trabajadores de la base de datos
        for (Trabajador t : getTrabajadores()) {
            daoTrabajadorSQL.delete(dao, t);
        }
        //Eliminamos los admins de la base de datos
        for (Admin a : getAdmins()) {
            daoAdminSQL.delete(dao, a);
        }
        //Eliminamos los productos de la base de datos
        for (Producto p : getCatalogo()) {
            daoProductoSQL.delete(dao, p);
        }

        //Recuperamos los productos del backup
        for (Producto p : catalogoBackup) {
            daoProductoSQL.insert(dao, p);
        }

        //Recuperamos los admins del backup
        for (Admin a : adminsBackup) {
            daoAdminSQL.insert(dao, a);
        }

        //Recuperamos los trabajadores del backup
        for (Trabajador t : trabajadoresBackup) {
            daoTrabajadorSQL.insert(dao, t);
        }

        //Recuperamos los clientes del backup
        for (Cliente c : clientesBackup) {
            daoClienteSQL.insert(dao, c);
            if (!c.pedidosPersistencia().isEmpty()) {
                for (Pedido p : c.pedidosPersistencia()) {
                    daoPedidoSQL.insert(dao, p, c);
                    daoPedidoProductosSQL.insert(dao, p);
                }
            }
            if (!c.carroPersistencia().isEmpty()) {
                for (Producto p : c.carroPersistencia()) {
                    daoCarroSQL.insert(dao, c, p);
                }
            }
        }

        for (Trabajador t : trabajadoresBackup) {
            if (!t.pedidosAsignadosPersistencia().isEmpty()) {
                for (Pedido p : t.pedidosAsignadosPersistencia()) {
                    daoPedidoSQL.updateTrabajador(dao, p, t);
                }
            }
        }
        return true;
    }

    public void adjuntaCorreosExcel(String email) {
        ArrayList<Pedido> listado = getTodosPedidos();
        Persistencia.adjuntaCorreos(listado);
        EnvioMail.enviarExcelCorreo(email);
    }

    public void actualizaComentario(PedidoClienteDataClass p, String comentarioNuevo) {
        for (Pedido pedido : getTodosPedidos()) {
            if (p.getIdPedido() == pedido.getId()) {
                pedido.setComentario(comentarioNuevo);
                Persistencia.guardaActualizaPedido(pedido);
            }
        }
        Persistencia.guardaAdminsPersistencia(getAdmins());
        Persistencia.guardaClientesPersistencia(getClientes());
        Persistencia.guardaTrabajadoresPersistencia(getTrabajadores());
    }

    public void actualizaProductoDAO(Producto p) {
        daoProductoSQL.update(dao, p);
    }

    public void actualizaPedidoDAO(PedidoClienteDataClass p, String comentarioNuevo) {
        for (Pedido pedido : getTodosPedidos()) {
            if (pedido.getId() == p.getIdPedido()) {
                pedido.setComentario(comentarioNuevo);
                daoPedidoSQL.update(dao, pedido);
            }
        }
    }
}
