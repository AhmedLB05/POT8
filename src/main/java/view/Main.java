package view;

import comunications.EnvioMail;
import comunications.EnvioTelegram;
import controller.Controlador;
import models.*;
import persistencia.Persistencia;
import utils.Utils;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final Scanner S = new Scanner(System.in);

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        datosPrueba(controlador);
        do {
            System.out.println("\n");
            //System.out.println("        Bienvenidos a nuestra tienda online");
            System.out.println("""
                    ███████╗███████╗██████╗ ███╗   ██╗ █████╗ ███╗   ██╗███████╗██╗  ██╗ ██████╗ ██████╗\s
                    ██╔════╝██╔════╝██╔══██╗████╗  ██║██╔══██╗████╗  ██║██╔════╝██║  ██║██╔═══██╗██╔══██╗
                    █████╗  █████╗  ██████╔╝██╔██╗ ██║███████║██╔██╗ ██║███████╗███████║██║   ██║██████╔╝
                    ██╔══╝  ██╔══╝  ██╔══██╗██║╚██╗██║██╔══██║██║╚██╗██║╚════██║██╔══██║██║   ██║██╔═══╝\s
                    ██║     ███████╗██║  ██║██║ ╚████║██║  ██║██║ ╚████║███████║██║  ██║╚██████╔╝██║    \s
                    ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝    \s
                                                                                                        \s
                    """);
            System.out.println("""
                    █████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗█████╗
                    ╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝╚════╝
                    """);
            Object user = menuInicio(controlador);
            if (user != null) {
                menuUsuario(controlador, user);
            }
        } while (true);
    }

    private static void datosPrueba(Controlador controlador) {
        boolean clientesVacios = false, trabajadoresVacios = false, adminsVacios = false, productosVacios = false;

        if (controlador.getClientes().isEmpty()) clientesVacios = true;
        if (controlador.getTrabajadores().isEmpty()) trabajadoresVacios = true;
        if (controlador.getAdmins().isEmpty()) adminsVacios = true;
        if (controlador.getCatalogo().isEmpty()) productosVacios = true;

        if (!clientesVacios || !trabajadoresVacios || !adminsVacios || !productosVacios) {
            System.out.println("____________________________________________________________________________________________________");
            if (!clientesVacios)
                System.out.println(" - Hay " + controlador.getClientes().size() + " cliente/s ya cargado/s en el sistema");
            if (!trabajadoresVacios)
                System.out.println(" - Hay " + controlador.getTrabajadores().size() + " trabajador/es ya cargado/s en el sistema");
            if (!adminsVacios)
                System.out.println(" - Hay " + controlador.getAdmins().size() + " admin/s ya cargado/s en el sistema");
            if (!productosVacios)
                System.out.println(" - Hay " + controlador.getCatalogo().size() + " producto/s ya cargado/s en el sistema");
            System.out.println("____________________________________________________________________________________________________");
            Utils.pulsaParaContinuar();
        }
        if (clientesVacios) {
            String respuesta;
            do {
                System.out.print("¿Desea agregar datos de CLIENTES de prueba? (S/N): ");
                respuesta = S.nextLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"))
                    System.out.println(" * ERROR DEBE RESPONDER CON S o N");
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.println("""
                        DATOS DE INICIO DE SESION CLIENTES
                        ==================================================================================
                        Cliente:
                            Email:  ahmedlb26205@gmail.com
                            Nombre: Ahmed
                            Clave:  123
                        
                        Cliente:
                            Email:  marcos.lara.0610@fernando3martos.com
                            Nombre: Marcos
                            Clave:  123
                        """);
                controlador.iniciaDatosCliente();
                Utils.pulsaParaContinuar();
            }
        }
        if (trabajadoresVacios) {
            String respuesta;
            do {
                System.out.print("¿Desea agregar datos de TRABAJADORES de prueba? (S/N): ");
                respuesta = S.nextLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"))
                    System.out.println(" * ERROR DEBE RESPONDER CON S o N");
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.println("""
                        DATOS DE INICIO DE SESION TRABAJADORES
                        ==================================================================================
                        Trabajador:
                            email: ahmed.lhaouchi.2602@fernando3martos.com
                            clave: 123
                        
                        Trabajador:
                            email: marcoscano2005@gmail.com
                            clave: 123
                        """);
                controlador.iniciaDatosTrabajadores();
                Utils.pulsaParaContinuar();
            }


        }
        if (adminsVacios) {
            String respuesta;
            do {
                System.out.print("¿Desea agregar datos de ADMINS de prueba? (S/N): ");
                respuesta = S.nextLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"))
                    System.out.println(" * ERROR DEBE RESPONDER CON S o N");
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) {

                System.out.println("""
                        DATOS DE INICIO DE SESION ADMIN
                        ==================================================================================
                        Admin 1:
                            email: admin@admin.com
                            clave: admin
                        """);
                controlador.iniciaDatosAdmin();
                Utils.pulsaParaContinuar();
            }
        }
        if (productosVacios) {
            String respuesta;
            do {
                System.out.print("¿Desea agregar datos de PRODUCTOS de prueba? (S/N): ");
                respuesta = S.nextLine();
                if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"))
                    System.out.println(" * ERROR DEBE RESPONDER CON S o N");
            } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n"));
            if (respuesta.equalsIgnoreCase("s")) controlador.iniciaDatosCatalogo();
        }

    }

    private static Object menuInicio(Controlador controlador) {
        Object usuarioLog = null;
        do {
            if (!controlador.accesoInvitado()) usuarioLog = menuAccesoNormal(controlador, usuarioLog);
            else usuarioLog = menuAccesoInvitado(controlador, usuarioLog);
            Utils.pulsaParaContinuar();
        } while (usuarioLog == null);
        return usuarioLog;
    }

    private static Object menuAccesoNormal(Controlador controlador, Object usuarioLog) {
        int op;

        do {
            try {
                System.out.println();
                System.out.println(" 1. - Registrarse");
                System.out.println(" 2. - Iniciar sesión");
                System.out.print(" - Marque su opción: ");
                op = Integer.parseInt(new Scanner(System.in).nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nERROR AL INTRODUCIR UNA OPCIÓN\n");
            }
        } while (true);

        switch (op) {
            case 1: //Registrarse
                registroCliente(controlador);
                break;
            case 2: //Iniciar sesión
                usuarioLog = login(controlador);
                if (usuarioLog == null) System.out.println(" * ERROR AL INICIAR SESIÓN");
                break;
            default:
                System.out.println(" * ERROR AL SELECCIONAR LA OPCIÓN");
                break;
        }
        return usuarioLog;
    }

    private static Object menuAccesoInvitado(Controlador controlador, Object usuarioLog) {
        int op;

        do {
            try {
                System.out.println();
                System.out.println(" 1. - Ver el catálogo");
                System.out.println(" 2. - Registrarse");
                System.out.println(" 3. - Iniciar sesión");
                System.out.print(" - Marque su opción: ");
                op = Integer.parseInt(new Scanner(System.in).nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("\nERROR AL INTRODUCIR UNA OPCIÓN\n");
            }
        } while (true);

        switch (op) {
            case 1: //Ver el catálogo
                pintaCatalogo(controlador);
                break;
            case 2: //Registrarse
                registroCliente(controlador);
                break;
            case 3: //Iniciar sesión
                usuarioLog = login(controlador);
                if (usuarioLog == null) System.out.println(" * ERROR AL INICIAR SESIÓN");
                break;
            default:
                System.out.println(" * ERROR AL SELECCIONAR LA OPCIÓN");
                break;
        }
        return usuarioLog;
    }

    //Metodo login
    private static Object login(Controlador controlador) {
        Object usuario = null;
        String emailIntro, claveIntro;
        System.out.print("\n - Introduzca su email: ");
        emailIntro = S.nextLine();
        System.out.print(" - Introduzca su contraseña: ");
        claveIntro = S.nextLine();
        usuario = controlador.login(emailIntro, claveIntro);
        //if (usuario == null) System.out.println("\nERROR AL INICIAR SESIÓN, VOLVIENDO AL MENÚ PRINCIPAL\n");
        //else menuUsuario(controlador, usuario);
        return usuario;
    }

    //Metodo para el registro de clientes
    private static void registroCliente(Controlador controlador) {
        String emailIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, claveIntro;
        int movilIntro;
        System.out.println();
        System.out.println("        Bienvenido al registro de clientes");
        System.out.println("===================================================");

        //Aquí comprobamos que el email no exista ya
        do {
            System.out.print(" - Introduzca su email: ");
            emailIntro = S.nextLine();
            if (controlador.buscaClienteByEmail(emailIntro) != null)
                System.out.println("ERROR: EMAIL INTRODUCIDO YA EXISTE");
        } while (controlador.buscaClienteByEmail(emailIntro) != null);

        System.out.print(" - Introduzca su nombre: ");
        nombreIntro = S.nextLine();
        System.out.print(" - Introduzca su localidad: ");
        localidadIntro = S.nextLine();
        System.out.print(" - Introduzca su provincia: ");
        provinciaIntro = S.nextLine();
        System.out.print(" - Introduzca su direccion: ");
        direccionIntro = S.nextLine();
        do {
            try {
                System.out.print(" - Introduzca su número de telefono: ");
                movilIntro = Integer.parseInt(S.nextLine());
                if (movilIntro > 99999999 && movilIntro <= 999999999) {
                    break;
                } else {
                    System.out.println(" * ERROR: El número de teléfono debe tener 9 dígitos.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR NUMERO DE TELEFONO");
            }
        } while (true);
        System.out.print(" - Introduzca su clave: ");
        claveIntro = S.nextLine();

        if (verificacionCliente(emailIntro)) {
            if (controlador.registraCliente(emailIntro, claveIntro, nombreIntro, localidadIntro, provinciaIntro, direccionIntro, movilIntro)) {
                System.out.println("\nREGISTRO EXISTOSO\n");
            } else System.out.println("\nERROR: REGISTRO ERRÓNEO\n");
        }
    }

    //Metodo que se encarga de verificar el token del cliente (inventado por Ahmed)
    private static boolean verificacionCliente(String email) {
        int token = Utils.generaTokenRegistro(), tokenIntro;
        System.out.println("\n\nA continuación se le va a enviar un código de verificación a su correo");
        if (EnvioMail.enviaTokenRegistro(email, token)) {
            do {
                try {
                    System.out.print("\nIntroduzca el código recibido: ");
                    tokenIntro = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR CODIGO");
                }
            } while (true);


            return (token == tokenIntro);
        }
        return false;
    }

    //Metodo que pinta el catálogo
    private static void pintaCatalogo(Controlador controlador) {
        int cont = 0;
        String continuar;
        if (controlador.getCatalogo().isEmpty()) System.out.println(" * ERROR EL CATÁLOGO ESTÁ VACIO");
        for (Producto p : controlador.getCatalogo()) {
            if (cont == 5) {
                System.out.print("\nPulsa ENTER para continuar o introduzca cualquier tecla para SALIR: ");
                continuar = S.nextLine();
                if (!continuar.isEmpty()) break;
                //Utils.pulsaParaContinuar();
                cont = 0;
            }
            System.out.println(p.pintaProductoADetalle());
            cont++;
        }

    }

    //Metodo que pinta el menu según el tipo de usuario que sea
    private static void menuUsuario(Controlador controlador, Object user) {

        //Pintamos el menú de cliente y cogemos la opción
        if (user instanceof Cliente) {
            int opCliente;
            Cliente cliente = controlador.buscaClienteById(((Cliente) user).getId());
            do {
                System.out.println();
                System.out.println(" - Bienvenido " + cliente.getNombre() + ". Tiene " + controlador.getTotalPedidosPendientesEntregaCliente(cliente) + " pedido/s pendiente/s de entrega.");
                System.out.println(" - Usted inició sesión por última vez el " + controlador.getUltimoInicioSesion(cliente.getId()));
                System.out.println("""
                        1. Consultar el catálogo de productos
                        2. Realizar un pedido
                        3. Ver mis pedidos
                        4. Ver mis datos personales
                        5. Modificar mis datos personales
                        6. Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opCliente = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuCliente(controlador, opCliente, cliente);
            } while (opCliente != 6);

        }

        //Pintamos el menu de trabajador y cogemos la opcion
        if (user instanceof Trabajador) {
            Trabajador trabajadorRegistrado = controlador.buscaTrabajadorByID(((Trabajador) user).getId());
            int opTrabajador;
            do {
                System.out.println("\n\n - Bienvenido trabajador " + trabajadorRegistrado.getNombre() + ". Tiene " + trabajadorRegistrado.numPedidosPendientes() + " pedido/s pendiente/s.");
                System.out.println(" - Usted inició sesión por última vez el " + controlador.getUltimoInicioSesion(trabajadorRegistrado.getId()));
                System.out.println("""
                        1. Consultar los pedidos que tengo asignados
                        2. Modificar el estado de un pedido
                        3. Consultar el catálogo de productos
                        4. Modificar un producto
                        5. Ver el histórico de pedidos terminados
                        6. Ver mi perfil
                        7. Modificar mis datos personales
                        8. Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opTrabajador = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuTrabajador(controlador, opTrabajador, trabajadorRegistrado);
            } while (opTrabajador != 8);
        }

        //Pintamos el menu de admin y cogemos la opcion
        if (user instanceof Admin) {
            Admin admin = null;
            for (Admin a : controlador.getAdmins()) {
                if (a.getId() == ((Admin) user).getId()) admin = a;
            }
            int opAdmin;
            do {
                System.out.println("\n - Usted inició sesión por última vez el " + controlador.getUltimoInicioSesion(admin.getId()));
                pintaEstadisticasAdmin(controlador);
                System.out.print("""
                         1. - Ver todo el catálogo
                         2. - Editar un producto
                         3. - Ver un resumen de todos los clientes
                         4. - Ver un resumen de todos los pedidos
                         5. - Ver un resumen de todos los trabajadores
                         6. - Ver las estadísticas de la aplicación
                         7. - Cambiar el estado de un pedido
                         8. - Dar de alta un trabajador
                         9. - Dar de baja un trabajador
                        10. - Asignar un pedido a un trabajador
                        11. - Mostrar la configuración del programa
                        12. - Enviar listado de pedidos por correo
                        13. - Realizar/recuperar una copia de seguridad
                        14. - Salir
                        """);
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        opAdmin = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                menuAdmin(controlador, opAdmin, admin);
            } while (opAdmin != 14);
        }
    }

    //Metodo que pinta las estadisticas que le aparecen al admin
    private static void pintaEstadisticasAdmin(Controlador controlador) {
        System.out.println();
        System.out.println("Bienvenido Administrador. Tenemos " + controlador.pedidosSinTrabajador().size() + " pedido/s sin asignar. " + "Debe asignarlos a un trabajador.");
        System.out.println("===============================================");
        System.out.println("Número de clientes: " + controlador.getClientes().size());
        System.out.println("Número de trabajadores: " + controlador.getTrabajadores().size());
        System.out.println("Numero de pedidos: " + controlador.numPedidosTotales());
        System.out.println("Número de pedidos pendientes: " + controlador.numPedidosPendientes());
        System.out.println("Número de pedidos completados o cancelados: " + controlador.numPedidosCompletadosCancelados());
        System.out.println("Número de pedidos sin asignar: " + controlador.pedidosSinTrabajador().size());
        System.out.println("===============================================");
    }

    //Metodo que contiene el switch del menu administrador
    private static void menuAdmin(Controlador controlador, int opAdmin, Admin admin) {
        switch (opAdmin) {
            case 1: //Ver todoo el catálogo
                consultaCatalogo(controlador);
                break;
            case 2: //Editar un producto
                modificaProducto(controlador);
                break;
            case 3: //Ver un resumen de todos los clientes
                pintaResumenClientes(controlador);
                Utils.pulsaParaContinuar();
                break;
            case 4: //Ver un resumen de todos los pedidos
                pintaResumenPedidos(controlador);
                break;
            case 5: //Ver un resumen de todos los trabajadores
                pintaResumenTrabajadores(controlador);
                Utils.pulsaParaContinuar();
                break;
            case 6: //Ver las estadísticas de la aplicación
                pintaEstadisticasAdmin(controlador);
                Utils.pulsaParaContinuar();
                break;
            case 7: //Cambiar el estado de un pedido
                modificaEstadoComentarioPedidoByAdmin(controlador);
                break;
            case 8: //Dar de alta un trabajador
                altaTrabajador(controlador);
                break;
            case 9: //Dar de baja un trabajador
                bajaTrabajador(controlador);
                break;
            case 10: //Asignar un pedido a un trabajador
                asignaPedidoTrabajador(controlador);
                break;
            case 11: //Mostrar la configuración del programa (properties)
                muestraConfigProperties(controlador);
                Utils.pulsaParaContinuar();
                break;
            case 12: //Enviar listado de pedidos por correo
                enviaExcel(controlador, admin);
                Utils.pulsaParaContinuar();
                break;
            case 13: //Realizar una copia de seguridad
                menuBackup(controlador);
                Utils.pulsaParaContinuar();
                break;
            case 14:
                controlador.guardaUltimoInicioSesion(admin);
                controlador.guardaAdmin();
                controlador.guardaClientes();
                controlador.guardaCatalogo();
                controlador.guardaTrabajadores();
                Utils.mensajeCierraPrograma();
                break;
        }
    }

    private static void enviaExcel(Controlador controlador, Admin admin) {
        if (controlador.getTodosPedidos().isEmpty()) System.out.println(" * ERROR NO SE HAN REALIZADO PEDIDOS");
        else {
            System.out.print("Introduzca el correo al que desea enviar el listado de pedidos: ");
            String email = S.nextLine();

            controlador.adjuntaCorreosExcel(email);
        }
    }

    private static void menuBackup(Controlador controlador) {
        System.out.print("""
                1. - Realizar una copia de seguridad
                2. - Recuperar una copia de seguridad
                Introduce una opción:\s""");
        String op = S.nextLine();

        switch (op) {
            case "1": //Realizar una copia de seguridad
                realizaBackup(controlador);
                break;
            case "2": //Recuperar una copia de seguridad
                recuperaBackup(controlador);
                break;
            default:
                System.out.println("Opción incorrecta...");
                break;
        }
    }

    private static void recuperaBackup(Controlador controlador) {
        String respuesta = "";
        do {
            System.out.print("¿Quiere recuperar el backup desde la ruta por defecto 'data/backup' ? (S/N) (-1 para salir): ");
            respuesta = S.nextLine();
            if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n") && !respuesta.equalsIgnoreCase("-1"))
                System.out.println(" * ERROR RESPUESTA NO PERMITIDA");
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n") && !respuesta.equals("-1"));
        if (respuesta.equals("-1")) return;
        if (respuesta.equalsIgnoreCase("s")) {
            if (controlador.recuperaBackup()) {
                System.out.println(" - COPIA DE SEGURIDAD RECUPERADA CON EXITO");
                controlador.guardaClientes();
                controlador.guardaTrabajadores();
                controlador.guardaAdmin();
                controlador.guardaCatalogo();
            } else System.out.println(" * ERROR NO SE HA PODIDO RECUPERAR LA COPIA DE SEGURIDAD");
        }
        if (respuesta.equalsIgnoreCase("n")) {
            String ruta;
            System.out.println(" ====== CONSEJOS PARA INTRODUCIR LA RUTA ====== ");
            System.out.println(" - Escriba la ruta completa (por ejemplo: C:\\Usuarios\\Juan\\Documentos\\backup)");
            System.out.println(" - Asegúrese de que la carpeta ya exista o que tenga permisos para crearla");
            System.out.println(" - Use doble barra invertida '\\\\' en Windows, o barra normal '/' en Linux/Mac");
            System.out.println(" - Evite caracteres especiales no permitidos como: * ? < > | \" :");
            System.out.println(" - Si deja la ruta vacía y presiona Enter, se cancelará la operación");
            System.out.println("=================================================================================");
            Utils.pulsaParaContinuar();
            System.out.print("Introduzca la ruta desde donde desea recuperar la copia de seguridad: ");
            ruta = S.nextLine();
            if (controlador.recuperaBackupPersonalizado(ruta)) {
                System.out.println(" - COPIA DE SEGURIDAD RECUPERADA CON EXITO");
                controlador.guardaClientes();
                controlador.guardaTrabajadores();
                controlador.guardaAdmin();
                controlador.guardaCatalogo();
            } else System.out.println(" * ERROR NO SE HA PODIDO RECUPERAR LA COPIA DE SEGURIDAD");
        }

    }

    private static void realizaBackup(Controlador controlador) {
        String respuesta = "";
        do {
            System.out.print("¿Quiere usar la ruta por defecto: 'data/backup' ? (S/N) (-1 para salir): ");
            respuesta = S.nextLine();

            if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n") && !respuesta.equalsIgnoreCase("-1"))
                System.out.println(" * ERROR RESPUESTA NO PERMITIDA");
        } while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n") && !respuesta.equals("-1"));
        if (respuesta.equals("-1")) return;
        if (respuesta.equalsIgnoreCase("s")) {
            if (controlador.creaBackup()) System.out.println(" - COPIA DE SEGURIDAD EXITOSA");
            else System.out.println(" * ERROR NO SE HA PODIDO REALIZAR LA COPIA DE SEGURIDAD");
        }
        if (respuesta.equalsIgnoreCase("n")) {
            String ruta;
            System.out.println(" ====== CONSEJOS PARA INTRODUCIR LA RUTA ====== ");
            System.out.println(" - Escriba la ruta completa (por ejemplo: C:\\Usuarios\\Juan\\Documentos\\backup)");
            System.out.println(" - Asegúrese de que la carpeta ya exista o que tenga permisos para crearla");
            System.out.println(" - Use doble barra invertida '\\\\' en Windows, o barra normal '/' en Linux/Mac");
            System.out.println(" - Evite caracteres especiales no permitidos como: * ? < > | \" :");
            System.out.println(" - Si deja la ruta vacía y presiona Enter, se cancelará la operación");
            System.out.println("=================================================================================");
            Utils.pulsaParaContinuar();
            System.out.print("Introduzca la ruta donde desea realizar la copia de seguridad: ");
            ruta = S.nextLine();
            if (ruta.isBlank()) {
                System.out.println(" * ERROR RUTA VACÍA");
                return;
            }
            if (controlador.creaBackupPersonalizado(ruta)) System.out.println(" - COPIA DE SEGURIDAD EXITOSA");
            else System.out.println(" * ERROR NO SE HA PODIDO REALIZAR LA COPIA DE SEGURIDAD");
        }
    }

    private static void muestraConfigProperties(Controlador controlador) {
        ArrayList<String> config = controlador.configProperties();

        for (String c : config) {
            System.out.println(c);
        }
    }

    private static void asignaPedidoTrabajador(Controlador controlador) {
        ArrayList<Pedido> pedidosSinAsignar = controlador.pedidosSinTrabajador();

        if (pedidosSinAsignar.isEmpty()) {
            System.out.println("No se ha realizado ningún pedido o no hay pedidos para asignar...");
            Utils.pulsaParaContinuar();
        } else if (controlador.getTrabajadores().isEmpty()) System.out.println(" * ERROR NO HAY TRABAJADORES");
        else {
            Pedido pedidoTemp = null;
            Trabajador trabajadorTemp = null;

            pintaPedidosSinAsignar(pedidosSinAsignar);

            int pedidoSeleccionado;
            do {
                System.out.print("\nIntroduce el pedido que deseas asignar: ");
                try {
                    pedidoSeleccionado = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL ELEGIR EL PEDIDO");
                }
            } while (true);

            try {
                pedidoTemp = pedidosSinAsignar.get(pedidoSeleccionado - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(" * ERROR AL ELEGIR EL PEDIDO");
            }

            pintaResumenTrabajadores(controlador);

            System.out.print("Introduce el trabajador para asignar un pedido: ");
            String trabajadorSeleccionado = S.nextLine();
            try {
                trabajadorTemp = controlador.getTrabajadores().get(Integer.parseInt(trabajadorSeleccionado) - 1);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                System.out.println(" * ERROR AL ELEGIR EL TRABAJADOR");
            }

            if (pedidoTemp == null || trabajadorTemp == null)
                System.out.println(" * ERROR NO SE HAN ENCONTRADO LOS DATOS");
            else {
                if (controlador.asignaPedido(pedidoTemp.getId(), trabajadorTemp.getId())) {
                    System.out.println(" - Pedido asignado a " + trabajadorTemp.getNombre() + " con éxito");
                    controlador.guardaTrabajadores();
                    controlador.guardaCatalogo();
                    EnvioTelegram.enviaMensajeTrabajadorPedidoAsignado(trabajadorTemp, pedidoTemp);

                    PedidoClienteDataClass dataTemp = null;
                    for (Trabajador t : controlador.getTrabajadores()) {
                        for (PedidoClienteDataClass p : controlador.getPedidosAsignadosTrabajador(t.getId())) {
                            if (p.getIdPedido() == pedidoTemp.getId()) dataTemp = p;
                        }
                    }
                    EnvioMail.enviaCorreoPedido(trabajadorTemp, dataTemp, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                } else System.out.println("Ha ocurrido un error...");
            }

        }
    }

    //Metodo que pinta los pedidos sin asignar
    private static void pintaPedidosSinAsignar(ArrayList<Pedido> pedidosSinAsignar) {
        pintaPedidosSinData(pedidosSinAsignar);
    }

    //Metodo que pinta los pedidos de la clase Pedido
    private static void pintaPedidosSinData(ArrayList<Pedido> pedidos) {
        int cont = 1;
        if (pedidos.isEmpty()) System.out.println("No tienes pedidos...");
        else {
            for (Pedido p : pedidos) {
                System.out.println(" - Pedido " + cont);
                System.out.println(p);
                Utils.pulsaParaContinuar();
                cont++;
            }
        }
    }

    //Metodo para dar de baja a un trabajador
    private static void bajaTrabajador(Controlador controlador) {
        if (controlador.getTrabajadores().isEmpty()) System.out.println(" * ERROR NO HAY TRABAJADORES EN EL SISTEMA");
        else {
            int id;
            do {
                try {
                    System.out.print(" - Introduzca el id del trabajador a dar de baja: ");
                    id = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR LA ID DEL TRABAJADOR");
                }
            } while (true);

            Trabajador t = controlador.buscaTrabajadorByID(id);
            if (t != null) {
                System.out.println(" - El trabajador seleccionado es: ");
                System.out.println(t);

                String opcion;
                do {
                    System.out.print("¿Desea dar de baja a ese trabajador? (SI / NO): ");
                    opcion = S.nextLine();

                    if (opcion.equalsIgnoreCase("si")) {
                        if (t.numPedidosPendientes() == 0) {
                            controlador.bajaTrabajador(t);
                            System.out.println(" - Trabajador dado de baja correctamente");
                            ArrayList<Pedido> pedidosSinAsignar = controlador.pedidosSinTrabajador();
                            Trabajador candidato = controlador.buscaTrabajadorCandidatoParaAsignar();

                            if (!pedidosSinAsignar.isEmpty() && candidato != null) {
                                for (Pedido p : pedidosSinAsignar) {
                                    controlador.asignaPedido(p.getId(), candidato.getId());
                                }

                                for (Pedido pedido : controlador.getTodosPedidos()) {
                                    for (PedidoClienteDataClass pDataClass : controlador.getPedidosAsignadosTrabajador(candidato.getId())) {
                                        if (pedido.getId() == pDataClass.getIdPedido())
                                            EnvioMail.enviaCorreoPedido(candidato, pDataClass, "SE LE HA ASIGNADO UN NUEVO PEDIDO");
                                    }
                                }
                            }
                            Utils.pulsaParaContinuar();
                        } else
                            System.out.println(" * ERROR NO SE HA DADO DE BAJA AL TRABAJADOR POR TENER PEDIDOS PENDIENTES"); //TODO REVISAR
                    } else if (opcion.equalsIgnoreCase("no")) {
                        System.out.println(" * CANCELANDO BAJA DEL TRABAJADOR");
                        Utils.pulsaParaContinuar();
                    } else System.out.println(" * ERROR NO INTRODUCIDA UNA OPCION CORRECTA");
                } while (!opcion.equalsIgnoreCase("si") && !opcion.equalsIgnoreCase("no"));
            } else {
                System.out.println(" * ERROR EL TRABAJADOR NO SE HA ENCONTRADO");
                Utils.pulsaParaContinuar();
            }
        }
    }

    //Metodo para dar de alta a un trabajador
    private static void altaTrabajador(Controlador controlador) {
        System.out.print(" - Introduzca el nombre del nuevo trabajador: ");
        String nombre = S.nextLine();
        System.out.print(" - Introduce la clave del nuevo trabajador: ");
        String clave = S.nextLine();
        String email;
        do {
            System.out.print(" - Introduzca el email del nuevo trabajador: ");
            email = S.nextLine();
            if (controlador.buscaTrabajadorByEmail(email) != null)
                System.out.println(" * ERROR EL EMAIL INTRODUCIDO YA EXISTE EN EL SISTEMA");
        } while (controlador.buscaTrabajadorByEmail(email) != null);
        int movilIntro;

        do {
            try {
                System.out.print(" - Introduzca el móvil del nuevo trabajador: ");
                movilIntro = Integer.parseInt(S.nextLine());
                if (movilIntro > 99999999 && movilIntro <= 999999999) {
                    break;
                } else {
                    System.out.println(" * ERROR: El número de teléfono debe tener 9 dígitos.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR NUMERO DE TELEFONO");
            }
        } while (true);


        int token = Utils.generaTokenRegistro();

        if (EnvioMail.enviaTokenRegistro(email, token)) {
            System.out.print(" - Introduzca el token que ha recibido por correo: ");
            String tokenIntro = S.nextLine();
            if (tokenIntro.equalsIgnoreCase(String.valueOf(token))) {
                if (controlador.nuevoTrabajador(email, clave, nombre, movilIntro)) {
                    System.out.println(" - TRABAJADOR DADO DE ALTA CORRECTAMENTE");
                    controlador.guardaTrabajadores();
                } else System.out.println(" * ERROR NO SE HA PODIDO DAR DE ALTA AL TRABAJADOR");
            }
        } else System.out.println(" * ERROR AL VERIFICAR EMAIL, TRABAJADOR NO DADO DE ALTA");
    }

    //Metodo que pinta un resumen de todos los pedidos para el admin
    private static void pintaResumenPedidos(Controlador controlador) {
        if (controlador.getTodosPedidos().isEmpty()) {
            System.out.println(" * ERROR NO HAY PEDIDOS QUE MOSTRAR");
            Utils.pulsaParaContinuar();
        } else {
            System.out.println("¿QUE PEDIDOS QUIERES QUE TE MOSTREMOS?");
            System.out.println("""
                    1. - Todos los pedidos
                    2. - Pedidos entregados
                    3. - Pedidos cancelados
                    4. - Pedidos pendientes
                    """);
            int op;
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    op = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (op == 1 || op == 2) {
                int contEntregados = 0;
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("""
                                         ┏┓   ┓• ┓                   ┓
                                         ┃┃┏┓┏┫┓┏┫┏┓┏  ┏┓┏┓╋┏┓┏┓┏┓┏┓┏┫┏┓┏
                                         ┣┛┗ ┗┻┗┗┻┗┛┛  ┗ ┛┗┗┛ ┗ ┗┫┗┻┗┻┗┛┛
                                                                    ┛
                        """);
                System.out.println("╚════════════════════════════════════════════╝");
                for (Pedido p : controlador.getTodosPedidos()) {
                    if (p.getEstado() == 3) {
                        System.out.println(p);
                        contEntregados++;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (contEntregados != 0) Utils.pulsaParaContinuar();
            }
            if (op == 1 || op == 3) {
                int contCancelados = 0;
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("""
                                          ┏┓   ┓• ┓             ┓   ┓  \s
                                          ┃┃┏┓┏┫┓┏┫┏┓┏  ┏┏┓┏┓┏┏┓┃┏┓┏┫┏┓┏
                                          ┣┛┗ ┗┻┗┗┻┗┛┛  ┗┗┻┛┗┗┗ ┗┗┻┗┻┗┛┛
                                                                         \s
                        """);
                System.out.println("╚════════════════════════════════════════════╝");
                for (Pedido p : controlador.getTodosPedidos()) {
                    if (p.getEstado() == 4) {
                        System.out.println(p);
                        contCancelados++;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (contCancelados != 0) Utils.pulsaParaContinuar();
            }
            if (op == 1 || op == 4) {
                int contPendientes = 0;
                System.out.println("\n╔════════════════════════════════════════════╗");
                System.out.println("""
                                          ┏┓   ┓• ┓            ┓•       \s
                                          ┃┃┏┓┏┫┓┏┫┏┓┏  ┏┓┏┓┏┓┏┫┓┏┓┏┓╋┏┓┏
                                          ┣┛┗ ┗┻┗┗┻┗┛┛  ┣┛┗ ┛┗┗┻┗┗ ┛┗┗┗ ┛
                                                          ┛               \s
                        """);
                System.out.println("╚════════════════════════════════════════════╝");
                for (Pedido p : controlador.getTodosPedidos()) {
                    if (p.getEstado() == 0 || p.getEstado() == 1 || p.getEstado() == 2) {
                        System.out.println(p);
                        contPendientes++;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (contPendientes != 0) Utils.pulsaParaContinuar();
            }

        }
    }

    //Metodo que pinta un resumen de los trabajadores para el admin
    private static void pintaResumenTrabajadores(Controlador controlador) {
        int cont = 1;
        if (controlador.getTrabajadores().isEmpty())
            System.out.println(" * ERROR NO HAY TRABAJADORES REGISTRADOS EN EL SISTEMA");
        else {
            for (Trabajador t : controlador.getTrabajadores()) {
                System.out.println(cont + " - ID: " + t.getId() + " - " + t.getNombre() + " movil: " + t.getMovil() + " - email: " + t.getEmail() + "\n");
                cont++;
            }
        }
    }

    //Metodo que pinta un resumen de los clientes para el admin
    private static void pintaResumenClientes(Controlador controlador) {
        if (controlador.getClientes().isEmpty())
            System.out.println(" * ERROR NO HAY CLIENTES REGISTRADOS EN EL SISTEMA");
        else {
            for (Cliente c : controlador.getClientes()) {
                System.out.println("- ID: " + c.getId() + " - " + c.getNombre() + " - " + c.getLocalidad() + "(" + c.getProvincia() + ") - email: " + c.getEmail() + " - movil: " + c.getMovil());
            }
        }
    }

    //Metodo que modifica el estado o comentario de un pedido a diferencia del trabajador en este metodo no se verifica que el pedido esté asignado al trabajador o no
    private static void modificaEstadoComentarioPedidoByAdmin(Controlador controlador) {
        int op;
        do {
            System.out.println(" === MODIFICADOR DE ESTADO PEDIDOS === ");
            System.out.println("""
                    1. - Modificar el estado
                    2. - Añadir o modificar un comentario
                    3. - Salir
                    =======================================""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    op = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (op) {
                case 1:
                    if (!controlador.getTodosPedidosClienteDataClass().isEmpty()) {
                        modificaEstadoPedidoByAdmin(controlador);
                    } else System.out.println("\n\n * ERROR NO HAY PEDIDOS QUE MODIFICAR\n\n");
                    break;
                case 2:
                    if (!controlador.getTodosPedidosClienteDataClass().isEmpty()) {
                        modificaComentarioPedidoByAdmin(controlador);
                    } else System.out.println("\n\n * ERROR NO HAY PEDIDOS QUE MODIFICAR\n\n");
                    break;
                case 3:
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (op != 3);
        controlador.guardaCatalogo();
    }

    //Metodo que modifica el comentario de un pedido (menu admin)
    private static void modificaComentarioPedidoByAdmin(Controlador controlador) {
        int numPedido;
        System.out.println();
        System.out.println("=======================================================================");
        System.out.println(" - BIENVENIDO A MODIFICAR EL COMENTARIO DE UN PEDIDO A TU CARGO");
        System.out.println("=======================================================================");
        System.out.println();

        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();

        int cont = 1;
        for (PedidoClienteDataClass pedidoSeleccionado : controlador.getTodosPedidosClienteDataClass()) {
            System.out.println(" ***** PEDIDO " + cont + " ***** \n");
            System.out.println(pedidoSeleccionado);
            pedidosCopia.add(pedidoSeleccionado);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cont++;
            Utils.pulsaParaContinuar();
        }
        do {
            try {
                System.out.print("Introduce el pedido: ");
                numPedido = Integer.parseInt(S.nextLine());
                numPedido--;
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DEL PEDIDO");
            }
        } while (true);
        System.out.println("Este es el pedido seleccionado: ");
        PedidoClienteDataClass p = pedidosCopia.get(numPedido);
        System.out.println(p);
        Utils.pulsaParaContinuar();
        System.out.print("\nIntroduzca el comentario a añadir: ");
        String comentarioNuevo = S.nextLine();
        p.setComentario(comentarioNuevo);

        Cliente c = null;
        PedidoClienteDataClass pedidoCambiado = null;

        for (Cliente cliente : controlador.getClientes()) {
            for (Pedido pedido : cliente.getPedidos()) {
                if (pedido.getId() == p.getIdPedido()) {
                    c = cliente;
                    break;
                }
            }
        }
        for (PedidoClienteDataClass pedidoData : controlador.getTodosPedidosClienteDataClass()) {
            if (pedidoData.getIdPedido() == p.getIdPedido()) pedidoCambiado = pedidoData;
        }
        if (c != null) {
            EnvioMail.enviaCorreoPedidoEstado(c, pedidoCambiado);
        } else System.out.println(" * ERROR CON EL CLIENTE");

    }

    //Metodo que modifica el estado de un pedido (menu admin)
    private static void modificaEstadoPedidoByAdmin(Controlador controlador) {
        int numPedido = 0;
        System.out.println();
        System.out.println(" - BIENVENIDO A MODIFICAR EL ESTADO DE UN PEDIDO A TU CARGO");
        System.out.println();

        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();

        int cont = 1;
        if (!controlador.getTodosPedidosClienteDataClass().isEmpty()) {
            for (PedidoClienteDataClass pedidoSeleccionado : controlador.getTodosPedidosClienteDataClass()) {
                System.out.println(" ***** PEDIDO " + cont + " ***** \n");
                System.out.println(pedidoSeleccionado);
                pedidosCopia.add(pedidoSeleccionado);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            do {
                try {
                    System.out.print("Introduce el pedido: ");
                    numPedido = Integer.parseInt(S.nextLine());
                    numPedido--;

                    if (numPedido < 0 || numPedido >= controlador.getTodosPedidosClienteDataClass().size()) {
                        System.out.println(" * ERROR NÚMERO FUERA DE RANGO");
                        numPedido = -1; // Forzar repetir el bucle
                    }

                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR EL NÚMERO DEL PEDIDO");
                }
            } while (numPedido == -1);
            System.out.println("Este es el pedido seleccionado: ");
            PedidoClienteDataClass p = pedidosCopia.get(numPedido);
            System.out.println(p);
            Utils.pulsaParaContinuar();
            int numEstado;
            System.out.println(" === Selecciona el nuevo estado === ");
            System.out.print("""
                    1. En preparación
                    2. Enviado
                    3. Entregado
                    4. Cancelado
                    """);
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    numEstado = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (controlador.cambiaEstadoPedido(p.getIdPedido(), numEstado)) {
                System.out.println(" - Se ha modificado el estado del pedido");
                Cliente c = null;
                PedidoClienteDataClass pedidoCambiado = null;

                for (Cliente cliente : controlador.getClientes()) {
                    for (Pedido pedido : cliente.getPedidos()) {
                        if (pedido.getId() == p.getIdPedido()) {
                            c = cliente;
                            break;
                        }
                    }
                }
                for (PedidoClienteDataClass pedidoData : controlador.getTodosPedidosClienteDataClass()) {
                    if (pedidoData.getIdPedido() == p.getIdPedido()) pedidoCambiado = pedidoData;
                }
                if (c != null) {
                    EnvioMail.enviaCorreoPedidoEstado(c, pedidoCambiado);
                } else System.out.println(" * ERROR CON EL CLIENTE");
            }
        } else System.out.println(" * ERROR NO HAY PEDIDOS PARA MODIFICAR");
    }

    //Metodo que contiene el switch del menu trabajador
    private static void menuTrabajador(Controlador controlador, int opTrabajador, Trabajador trabajador) {
        switch (opTrabajador) {
            case 1: //Consultar los pedidos que tengo asignados
                consultaPedidosAsignados(controlador, trabajador);
                Utils.pulsaParaContinuar();
                break;
            case 2: //Modificar el estado de un pedido
                modificaEstadoComentarioPedido(controlador, trabajador);
                break;
            case 3: //Consultar el catálogo de productos
                consultaCatalogo(controlador);
                break;
            case 4: //Modificar un producto
                modificaProducto(controlador);
                break;
            case 5: //Ver el histórico de pedidos terminados
                historicoPedidosTerminados(trabajador, controlador);
                break;
            case 6: //Ver mi perfil
                pintaPerfilTrabajador(trabajador);
                break;
            case 7: //Modificar mis datos personales
                modificaDatosTrabajador(controlador, trabajador);
                break;
            case 8: //Salir
                controlador.guardaUltimoInicioSesion(trabajador);
                controlador.guardaAdmin();
                controlador.guardaClientes();
                controlador.guardaCatalogo();
                controlador.guardaTrabajadores();
                Utils.mensajeCierraPrograma();
                break;
        }
    }

    //Metodo que modifica un producto
    private static void modificaProducto(Controlador controlador) {
        int id, op;
        do {
            try {
                System.out.print("Introduzca el ID del producto a modificar: ");
                id = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL ID DEL PRODUCTO");
            }
        } while (true);
        Producto p = controlador.buscaProductoById(id);
        if (p == null) System.out.println(" * ERROR NO SE HA ENCONTRADO NINGÚN PRODUCTO");
        else {
            System.out.println("Has seleccionado este producto: ");
            System.out.println();
            System.out.println(p.pintaProductoADetalle());
            System.out.println();
            do {
                System.out.println(" === MODIFICA PRODUCTO ===");
                System.out.println("""
                        1. - Modificar todos los campos
                        2. - Modificar marca
                        3. - Modificar modelo
                        4. - Modificar descripción
                        5. - Modificar precio
                        6. - Salir""");
                do {
                    try {
                        System.out.print("Introduzca una opción: ");
                        op = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                    }
                } while (true);
                if (op == 1 || op == 2) {
                    System.out.print("Introduzca nueva marca del producto: ");
                    String marca = S.nextLine();
                    p.setMarca(marca);
                    controlador.guardaCatalogo();
                }
                if (op == 1 || op == 3) {
                    System.out.print("Introduzca nuevo modelo del producto: ");
                    String modelo = S.nextLine();
                    p.setModelo(modelo);
                    controlador.guardaCatalogo();
                }
                if (op == 1 || op == 4) {
                    System.out.print("Introduzca nueva descripción del producto: ");
                    String descripcion = S.nextLine();
                    p.setDescripcion(descripcion);
                    controlador.guardaCatalogo();
                }
                if (op == 1 || op == 5) {
                    float precio;
                    do {
                        try {
                            System.out.print("Introduzca nuevo precio: ");
                            precio = Float.parseFloat(S.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println(" * ERROR AL INTRODUCIR NUEVO PRECIO");
                        }
                    } while (true);
                    if (precio <= 0) System.out.println(" * ERROR EL PRECIO NO PUEDE SER NEGATIVO");
                    else {
                        p.setPrecio(precio);
                        controlador.guardaCatalogo();
                    }
                }
                if (op == 6) Utils.mensajeCierraPrograma();
            } while (op != 6);
        }
        Utils.mensajeGuardadoPersistencia(controlador.guardaCatalogo());
    }

    //Metodo que pinta todos los pedidos terminados/completados de un trabajador
    private static void historicoPedidosTerminados(Trabajador trabajador, Controlador controlador) {
        if (controlador.getPedidosCompletadosTrabajador(trabajador.getId()).isEmpty())
            System.out.println("\n * ERROR NO TIENES PEDIDOS COMPLETADOS EN TU PERFIL\n");
        else {
            System.out.println("""
                    ╔═╗╔═╗╔╦╗╦╔╦╗╔═╗╔═╗  ╔═╗╔═╗╔╦╗╔═╗╦  ╔═╗╔╦╗╔═╗╔╦╗╔═╗╔═╗
                    ╠═╝║╣  ║║║ ║║║ ║╚═╗  ║  ║ ║║║║╠═╝║  ║╣  ║ ╠═╣ ║║║ ║╚═╗
                    ╩  ╚═╝═╩╝╩═╩╝╚═╝╚═╝  ╚═╝╚═╝╩ ╩╩  ╩═╝╚═╝ ╩ ╩ ╩═╩╝╚═╝╚═╝
                    """);
            for (PedidoClienteDataClass p : controlador.getPedidosCompletadosTrabajador(trabajador.getId())) {
                System.out.println(p);
                System.out.println();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //Metodo que se encarga de modificar los datos de un trabajador
    private static void modificaDatosTrabajador(Controlador controlador, Trabajador trabajador) {
        int opcion;
        Trabajador trabajadorCambiaDatos = new Trabajador(trabajador);
        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("    MODIFICA DATOS PERSONALES");
            System.out.println("=================================");
            System.out.println("""
                    1. - Modificar todos los datos del cliente
                    2. - Modificar nombre
                    3. - Modificar contraseña
                    4. - Modificar email
                    5. - Modificar movil
                    6. - Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (opcion == 1 || opcion == 2) { //NOMBRE
                System.out.print("Introduzca nuevo nombre: ");
                String nombre = S.nextLine();
                trabajadorCambiaDatos.setNombre(nombre);
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setNombre(nombre);
            }
            if (opcion == 1 || opcion == 8) {
                String claveAnterior, claveNueva;
                System.out.print("Introduzca clave anterior: ");
                claveAnterior = S.nextLine();

                if (!trabajadorCambiaDatos.getPass().equals(claveAnterior))
                    System.out.println(" * ERROR CLAVE NO VÁLIDA");
                else {
                    System.out.println();
                    System.out.println(" * CLAVE CORRECTA");
                    System.out.print("Introduzca nueva clave: ");
                    claveNueva = S.nextLine();

                    if (claveNueva.equals(claveAnterior))
                        System.out.println(" * ERROR LA NUEVA CLAVE ES IGUAL A LA ANTERIOR");
                    else {
                        trabajadorCambiaDatos.setPass(claveNueva);
                        controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                        trabajador.setPass(claveNueva);
                        System.out.println(" - SU CLAVE HA SIDO CAMBIADA CON ÉXITO: " + claveNueva);
                    }
                }
                controlador.guardaTrabajadores();
            }
            if (opcion == 1 || opcion == 4) { //EMAIL
                System.out.print("Introduzca nuevo email: ");
                String email = S.nextLine();

                if (controlador.buscaTrabajadorByEmail(email) != null)
                    System.out.println(" * ERROR YA EXISTE UN CLIENTE CON ESTE EMAIL");
                else if (verificacionTrabajador(email)) {
                    trabajadorCambiaDatos.setEmail(email);
                    System.out.println(" - SU EMAIL HA SIDO CAMBIADO CON ÉXITO");
                } else System.out.println(" * ERROR AL VERTIFICAR EL NUEVO EMAIL");
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setEmail(email);
            }
            if (opcion == 1 || opcion == 5) { //MOVIL
                int movil;
                do {
                    try {
                        System.out.print("Introduzca nuevo numero de teléfono: ");
                        movil = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DE TELÉFONO");
                    }
                } while (true);
                if (!(movil > 99999999 && movil <= 999999999))
                    System.out.println(" * ERROR: NUMERO INTRODUCIDO ERRÓNEO");
                else trabajadorCambiaDatos.setMovil(movil);
                controlador.actualizaDatosTrabajador(trabajador, trabajadorCambiaDatos);
                trabajador.setMovil(movil);
            }
            if (opcion == 6) Utils.mensajeCierraPrograma();
        } while (opcion != 6);
    }

    //Metodo que se encarga de toda la verificacion del email de un trabajador enviandole un token
    private static boolean verificacionTrabajador(String email) {
        int token = Utils.generaTokenRegistro(), tokenIntro = 0;
        System.out.println("\n\nA continuación se le va a enviar un código de verificación a su correo");
        EnvioMail.enviaTokenRegistro(email, token);
        try {
            System.out.print("\nIntroduzca el código recibido: ");
            tokenIntro = Integer.parseInt(S.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ERROR AL INTRODUCIR CODIGO");
        }

        return (token == tokenIntro);
    }

    //Metodo que se encarga de pinta el perfil del trabajador
    private static void pintaPerfilTrabajador(Trabajador trabajador) {
        System.out.println(trabajador);
    }

    //Metodo que se encarga de modificar el estado y el comentario de un pedido asignado a un trabajador
    private static void modificaEstadoComentarioPedido(Controlador controlador, Trabajador trabajador) {
        int op;
        do {
            System.out.println(" === MODIFICADOR DE ESTADO PEDIDOS === ");
            System.out.println("""
                    1. - Modificar el estado
                    2. - Añadir o modificar un comentario
                    3. - Salir
                    =======================================""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    op = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (op) {
                case 1:
                    modificaEstadoPedido(controlador, trabajador);
                    break;
                case 2:
                    modificaComentarioPedido(controlador, trabajador);
                    break;
                case 3:
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (op != 3);
        Utils.mensajeGuardadoPersistencia(controlador.guardaCatalogo());
    }

    //Metodo que se encarga de modificar el comentario de un pedido asignado a un trabajador
    private static void modificaComentarioPedido(Controlador controlador, Trabajador trabajador) {
        System.out.println();
        System.out.println(" - BIENVENIDO A MODIFICAR EL COMENTARIO DE UN PEDIDO A TU CARGO");
        System.out.println("===============================================================");
        System.out.println();

        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();
        //PedidoClienteDataClass temp = null;
        int cont = 1;
        for (PedidoClienteDataClass pedidoSeleccionado : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
            System.out.println(" ***** PEDIDO " + cont + " ***** \n");
            System.out.println(pedidoSeleccionado);
            pedidosCopia.add(pedidoSeleccionado);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int numPedido;
        do {
            try {
                System.out.print("Introduce el pedido: ");
                numPedido = Integer.parseInt(S.nextLine());
                numPedido--;
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DEL PEDIDO");
            }
        } while (true);
        System.out.println("Este es el pedido seleccionado: ");
        PedidoClienteDataClass p = pedidosCopia.get(numPedido);
        System.out.println(p);
        Utils.pulsaParaContinuar();
        System.out.print("Introduzca el comentario a añadir: ");
        String comentarioNuevo = S.nextLine();
        controlador.actualizaComentario(p, comentarioNuevo);
        Pedido pedidoActualizado = null;
        for (Pedido pedidoBuscado : controlador.getTodosPedidos()) {
            if (pedidoBuscado.getId() == p.getIdPedido()) pedidoActualizado = pedidoBuscado;
        }
        Persistencia.guardaActualizaPedido(pedidoActualizado);
        Cliente c = null;
        PedidoClienteDataClass pedidoCambiado = null;
        for (Cliente cliente : controlador.getClientes()) {
            for (Pedido pedido : cliente.getPedidos()) {
                if (pedido.getId() == p.getIdPedido()) {
                    c = cliente;
                    break;
                }
            }
        }
        for (PedidoClienteDataClass pedidoData : controlador.getTodosPedidosClienteDataClass()) {
            if (pedidoData.getIdPedido() == p.getIdPedido()) pedidoCambiado = pedidoData;
        }
        if (c != null) {
            EnvioMail.enviaCorreoPedidoEstado(c, pedidoCambiado);
        } else System.out.println(" * ERROR CON EL CLIENTE");

    }

    //Metodo que se encarga de modificar el estado de un pedido asignado a un trabajador
    private static void modificaEstadoPedido(Controlador controlador, Trabajador trabajador) {
        System.out.println();
        System.out.println(" - BIENVENIDO A MODIFICAR EL ESTADO DE UN PEDIDO A TU CARGO");
        System.out.println();

        ArrayList<PedidoClienteDataClass> pedidosCopia = new ArrayList<>();

        int cont = 1;
        for (PedidoClienteDataClass pedidoSeleccionado : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
            System.out.println(" ***** PEDIDO " + cont + " ***** \n");
            System.out.println(pedidoSeleccionado);
            pedidosCopia.add(pedidoSeleccionado);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int numPedido;

        do {
            try {
                System.out.print("Introduce el pedido: ");
                numPedido = Integer.parseInt(S.nextLine());
                numPedido--;
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DEL PEDIDO");
            }
        } while (true);
        System.out.println("Este es el pedido seleccionado: ");
        PedidoClienteDataClass p = pedidosCopia.get(numPedido);
        System.out.println(p);
        System.out.println();
        Utils.pulsaParaContinuar();
        int numEstado;
        System.out.println(" === Selecciona el nuevo estado === ");
        System.out.print("""
                1. En preparación
                2. Enviado
                3. Entregado
                4. Cancelado
                """);
        do {
            try {
                System.out.print("Introduzca una opción: ");
                numEstado = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
            }
        } while (true);
        Cliente c = null;
        PedidoClienteDataClass pedidoCambiado = null;
        if (controlador.cambiaEstadoPedido(p.getIdPedido(), numEstado)) {
            System.out.println(" - Se ha modificado el estado del pedido");

            for (Cliente cliente : controlador.getClientes()) {
                for (Pedido pedido : cliente.getPedidos()) {
                    if (pedido.getId() == p.getIdPedido()) {
                        c = cliente;
                        break;
                    }
                }
            }
            for (PedidoClienteDataClass pedidoData : controlador.getTodosPedidosClienteDataClass()) {
                if (pedidoData.getIdPedido() == p.getIdPedido()) pedidoCambiado = pedidoData;
            }
            if (c != null) {
                EnvioMail.enviaCorreoPedidoEstado(c, pedidoCambiado);
            } else System.out.println(" * ERROR CON EL CLIENTE");
        }
    }

    //Metodo que consulta los pedidos que tengo asignados (siendo trabajador)
    private static void consultaPedidosAsignados(Controlador controlador, Trabajador trabajador) {
        if (trabajador.numPedidosPendientes() == 0) System.out.println("\n * NO TIENE PEDIDOS PENDIENTES");
        else {
            System.out.println();
            System.out.println(" - Tiene " + trabajador.numPedidosPendientes() + " pedidos pendientes");
            System.out.println();
            for (PedidoClienteDataClass p : controlador.getPedidosAsignadosTrabajador(trabajador.getId())) {
                System.out.println(p);
                System.out.println();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    //Metodo que contiene el switch del menu cliente TODO
    private static void menuCliente(Controlador controlador, int opCliente, Cliente cliente) {

        switch (opCliente) {
            case 1: //Consultar el catálogo de productos
                consultaCatalogo(controlador);
                break;
            case 2: //Realizar un pedido TODO
                realizaPedidoCliente(cliente, controlador);
                break;
            case 3: //Ver mis pedidos
                pintaPedidosCliente(cliente);
                Utils.pulsaParaContinuar();
                break;
            case 4: //Ver mis datos personales
                pintaDatosPersonalesCliente(cliente);
                Utils.pulsaParaContinuar();
                break;
            case 5: //Modificar mis datos personales
                modificaDatosCliente(controlador, cliente);
                break;
            case 6: //Salir
                controlador.guardaUltimoInicioSesion(cliente);
                controlador.guardaAdmin();
                controlador.guardaClientes();
                controlador.guardaCatalogo();
                controlador.guardaTrabajadores();
                Utils.mensajeCierraPrograma();
                break;
        }
    }

    //Metodo para realizar un pedido siendo cliente TODO
    private static void realizaPedidoCliente(Cliente cliente, Controlador controlador) {
        int op;
        do {

            System.out.println("\nActualmente tiene " + cliente.getCarro().size() + " productos en su carro.");
            System.out.println("""
                    1. Inserta un producto en el carro
                    2. Ver el carro
                    3. Eliminar un producto del carro
                    4. Confirmar el pedido
                    5. Cancelar el pedido
                    6. Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    op = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (op) {
                case 1: //Inserta un producto en el carro
                    insertaProductoCarro(controlador, cliente);
                    Utils.pulsaParaContinuar();
                    break;
                case 2: //Ver el carro
                    pintaCarroCliente(cliente);
                    Utils.pulsaParaContinuar();
                    break;
                case 3: //Eliminar un producto del carro
                    eliminaProducto(cliente, controlador);
                    Utils.pulsaParaContinuar();
                    break;
                case 4: //Confirmar el pedido
                    confirmaPedido(controlador, cliente);
                    Utils.pulsaParaContinuar();
                    break;
                case 5: //Cancelar el pedido
                    cancelaPedido(controlador, cliente);
                    Utils.pulsaParaContinuar();
                    break;
                case 6: //Salir
                    controlador.guardaClientes();
                    controlador.guardaTrabajadores();
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (op != 6);

    }

    //Metodo para cancelar un pedido
    private static void cancelaPedido(Controlador controlador, Cliente cliente) {
        ArrayList<Pedido> pedidosRealizados = cliente.getPedidos();

        if (pedidosRealizados.isEmpty()) System.out.println(" * ERROR NO SE HAN REALIZADO PEDIDOS");
        else {
            Pedido temp = seleccionaPedidoCliente(controlador, cliente);

            if (temp == null) System.out.println(" * ERROR NINGUN PEDIDO CANCELADO");
            else {
                System.out.println("¿Deseas cancelar el pedido? (SI/NO)");
                String cancelaPedido = S.nextLine();

                if (cancelaPedido.equalsIgnoreCase("si")) {
                    if (controlador.cancelaPedidoCliente(cliente.getId(), temp.getId())) {
                        System.out.println(" - El pedido ha sido cancelado con éxito");
                        controlador.guardaClientes();
                    }
                } else if (cancelaPedido.equalsIgnoreCase("no")) {
                    System.out.println(" * ERROR SE HA CANCELADO EL PROCESO");
                }
            }
        }
    }

    //Metodo para seleccionar un pedido hecho por el cliente
    private static Pedido seleccionaPedidoCliente(Controlador controlador, Cliente cliente) {
        ArrayList<Pedido> pedidos = cliente.getPedidos();
        int cont = 1;
        int pedidoSeleccionado;

        if (pedidos == null) return null;
        if (pedidos.isEmpty()) return null;

        ArrayList<Pedido> pedidosNoCancelado = new ArrayList<>();

        for (Pedido p : pedidos) {
            if (p.getEstado() != 4) pedidosNoCancelado.add(p);
        }

        if (pedidosNoCancelado.isEmpty()) return null;

        System.out.print("""
                ┏┓┏┓┳┓┳┳┓┏┓┏┓
                ┃┃┣ ┃┃┃┃┃┃┃┗┓
                ┣┛┗┛┻┛┻┻┛┗┛┗┛
                            \s
                """);
        for (Pedido p : pedidos) {
            System.out.println("Pedido " + cont);

            System.out.println(p);
            cont++;
        }
        do {
            try {
                System.out.print("Introduce el pedido (-1 para salir): ");
                pedidoSeleccionado = Integer.parseInt(S.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR INTRODUCIR UNA OPCIÓN");
            }
        } while (true);

        if (pedidoSeleccionado > 0) {
            Pedido pedidoElegido = null;

            try {
                pedidoElegido = pedidos.get(pedidoSeleccionado - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(" * ERROR AL ELEGIR EL PEDIDO");
            }

            if (pedidoElegido == null) return null;

            return controlador.buscaPedidoById(pedidoElegido.getId());
        }
        if (pedidoSeleccionado == -1) Utils.mensajeCierraPrograma();
        return null;
    }

    private static void confirmaPedido(Controlador controlador, Cliente cliente) {

        if (cliente.numProductosCarro() == 0) System.out.println("\n * ERROR EL CARRO ESTÁ VACIO");
        else {
            System.out.print("¿Quieres confirmar el pedido? (S/N): ");
            String confirmaPedido = S.nextLine();
            if (confirmaPedido.equalsIgnoreCase("s")) {
                if (controlador.confirmaPedidoCliente(cliente.getId())) {
                    System.out.println(" - El pedido se ha confirmado con éxito");
                    controlador.guardaClientes();
                    controlador.guardaTrabajadores();
                }

            } else System.out.println(" * ERROR LA CONFIRMACION DEL PEDIDO SE HA CANCELADO");
        }
    }

    //Metodo para eliminar un producto del carro de un cliente
    private static void eliminaProducto(Cliente cliente, Controlador controlador) {

        int posProducto;
        if (cliente.numProductosCarro() == 0) System.out.println("\n * ERROR EL CARRO ESTÁ VACIO");
        else {
            pintaCarroCliente(cliente);
            do {
                try {
                    System.out.print("Introduce el producto a eliminar ( -1 para salir): ");
                    posProducto = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR OPCION");
                }
            } while (true);

            Producto temp = null;
            if (posProducto != -1) {
                temp = cliente.getCarro().get(posProducto - 1);
            }

            if (temp == null) System.out.println(" * ERROR NO SE HA ENCONTRADO NINGÚN PRODUCTO");
            else {
                if (cliente.quitaProductoCarro(temp.getId())) {
                    controlador.guardaClientes();
                    System.out.println(" - Producto eliminado correctamente");

                } else System.out.println(" * ERROR AL ELIMINAR EL PRODUCTO DEL CARRO");
            }
        }

    }

    //Metodo para pinta el carro de un cliente
    private static void pintaCarroCliente(Cliente cliente) {
        int cont = 1;
        if (cliente.numProductosCarro() == 0) System.out.println(" * ERROR EL CARRO ESTÁ VACIO");
        else {
            System.out.println("""
                    ┏┓┏┓┳┓┳┓┏┓  ┏┓┓ ┳┏┓┳┓┏┳┓┏┓
                    ┃ ┣┫┣┫┣┫┃┃  ┃ ┃ ┃┣ ┃┃ ┃ ┣\s
                    ┗┛┛┗┛┗┛┗┗┛  ┗┛┗┛┻┗┛┛┗ ┻ ┗┛
                    """);
            for (Producto p : cliente.getCarro()) {
                System.out.println("Producto " + cont);
                System.out.println(p);
                cont++;
            }
            System.out.println();
            System.out.printf("Total sin IVA: %.2f\n", cliente.precioCarroSinIVA());
            System.out.printf("IVA total: %.2f\n", cliente.precioIVACarro(Utils.IVA));
            System.out.printf("Total con IVA: %.2f\n", cliente.precioCarroConIVA(Utils.IVA));

        }
    }

    //Metodo para insertar un producto en el carro de cliente
    private static void insertaProductoCarro(Controlador controlador, Cliente cliente) {
        Producto temp = null;
        int cont = 1;
        int productoSeleccionado;

        for (Producto p : controlador.getCatalogo()) {
            System.out.println("Producto " + cont);
            System.out.println(p);
            cont++;
        }

        do {
            do {
                try {
                    System.out.print("Introduce el número del producto ( -1 para salir): ");
                    productoSeleccionado = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" * ERROR AL INTRODUCIR OPCION");
                }
            } while (true);

            if (productoSeleccionado == -1) break;

            try {
                temp = controlador.getCatalogo().get(productoSeleccionado - 1);
                //temp = controlador.buscaProductoById(temp.getId());
            } catch (IndexOutOfBoundsException e) {
                System.out.println(" * ERROR INESPERADO");
            }
        } while (temp == null);

        if (temp != null && controlador.addProductoCarrito(cliente, temp.getId())) {
            controlador.guardaClientes();
            System.out.println(" - El producto se ha añadido al carrito correctamente");
        } else System.out.println(" * ERROR NO SE HA PODIDO AGREGAR AL CARRITO EL PRODUCTO");

    }

    //Metodo que pinta los datos personales de un cliente que le pasemos
    private static void pintaDatosPersonalesCliente(Cliente cliente) {
        System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("""
                                    ____        _                                                    _          \s
                                   |  _ \\  __ _| |_ ___  ___   _ __   ___ _ __ ___  ___  _ __   __ _| | ___  ___\s
                                   | | | |/ _` | __/ _ \\/ __| | '_ \\ / _ \\ '__/ __|/ _ \\| '_ \\ / _` | |/ _ \\/ __|
                                   | |_| | (_| | || (_) \\__ \\ | |_) |  __/ |  \\__ \\ (_) | | | | (_| | |  __/\\__ \\
                                   |____/ \\__,_|\\__\\___/|___/ | .__/ \\___|_|  |___/\\___/|_| |_|\\__,_|_|\\___||___/
                                                              |_|                                               \s
                                           \s
                """);
        System.out.println(cliente);
        System.out.println("──────────────────────────────────────────────────────────────────────────────────────────────────");
    }

    //Metodo que nos indica varias maneras de consultar el catálogo siendo cliente
    private static void consultaCatalogo(Controlador controlador) {
        int opcion;
        do {
            System.out.println();
            System.out.println("""
                    1. Ver todo el catálogo
                    2. Búsqueda por marca
                    3. Búsqueda por modelo
                    4. Búsqueda por descripción
                    5. Búsqueda por término
                    6. Búsqueda por precio
                    7. Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            switch (opcion) {
                case 1: //Ver tódo el catálogo
                    pintaCatalogo(controlador);
                    break;
                case 2: //Búsqueda por marca
                    buscaProductoMarca(controlador);
                    break;
                case 3: //Búsqueda por modelo
                    buscaProductoModelo(controlador);
                    break;
                case 4: //Búsqueda por descripción
                    buscaProductoDescripcion(controlador);
                    break;
                case 5: //Búsqueda por término
                    buscaProductoTermino(controlador);
                    break;
                case 6: //Búsqueda por precio
                    buscaProductoPrecio(controlador);
                    break;
                case 7: //Salir
                    Utils.mensajeCierraPrograma();
                    break;
            }
        } while (opcion != 7);
    }

    //Metodo que modifica los datos del cliente
    private static void modificaDatosCliente(Controlador controlador, Cliente cliente) {
        int opcion;
        do {
            System.out.println();
            System.out.println("=================================");
            System.out.println("    MODIFICA DATOS PERSONALES");
            System.out.println("=================================");
            System.out.println("""
                    1. - Modificar todos los datos del cliente
                    2. - Modificar nombre
                    3. - Modificar localidad
                    4. - Modificar provincia
                    5. - Modificar direccion
                    6. - Modificar móvil
                    7. - Modificar email
                    8. - Modificar clave
                    9. - Salir""");
            do {
                try {
                    System.out.print("Introduzca una opción: ");
                    opcion = Integer.parseInt(S.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR AL INTRODUCIR LA OPCIÓN");
                }
            } while (true);
            if (opcion == 1 || opcion == 2) {
                System.out.print("Introduzca nuevo nombre: ");
                String nombre = S.nextLine();
                cliente.setNombre(nombre);
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setNombre(nombre);
            }
            if (opcion == 1 || opcion == 3) {
                System.out.print("Introduzca nueva localidad: ");
                String localidad = S.nextLine();
                cliente.setLocalidad(localidad);
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setLocalidad(localidad);
            }
            if (opcion == 1 || opcion == 4) {
                System.out.print("Introduzca nueva provincia: ");
                String provincia = S.nextLine();
                cliente.setProvincia(provincia);
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setProvincia(provincia);
            }
            if (opcion == 1 || opcion == 5) {
                System.out.print("Introduzca nueva dirección: ");
                String direccion = S.nextLine();
                cliente.setDireccion(direccion);
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setDireccion(direccion);
            }
            if (opcion == 1 || opcion == 6) {
                int movil;
                do {
                    try {
                        System.out.print("Introduzca nuevo numero de teléfono: ");
                        movil = Integer.parseInt(S.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(" * ERROR AL INTRODUCIR EL NUMERO DE TELÉFONO");
                    }
                } while (true);
                if (!(movil > 99999999 && movil <= 999999999))
                    System.out.println(" * ERROR: NUMERO INTRODUCIDO ERRÓNEO");
                else cliente.setMovil(movil);
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setMovil(movil);
            }
            if (opcion == 1 || opcion == 7) {
                System.out.print("Introduzca nuevo email: ");
                String email = S.nextLine();

                if (controlador.buscaClienteByEmail(email) != null)
                    System.out.println(" * ERROR YA EXISTE UN CLIENTE CON ESTE EMAIL");
                else if (verificacionCliente(email)) {
                    cliente.setEmail(email);
                    System.out.println(" - SU EMAIL HA SIDO CAMBIADO CON ÉXITO");
                } else System.out.println(" * ERROR AL VERTIFICAR EL NUEVO EMAIL");
                controlador.actualizaDatosCliente(cliente, cliente);
                cliente.setEmail(email);
            }
            if (opcion == 1 || opcion == 8) {
                String claveAnterior, claveNueva;
                System.out.print("Introduzca clave anterior: ");
                claveAnterior = S.nextLine();

                if (!cliente.getClave().equals(claveAnterior)) System.out.println(" * ERROR CLAVE NO VÁLIDA");
                else {
                    System.out.println();
                    System.out.println(" * CLAVE CORRECTA");
                    System.out.print("Introduzca nueva clave: ");
                    claveNueva = S.nextLine();

                    if (claveNueva.equals(claveAnterior))
                        System.out.println(" * ERROR LA NUEVA CLAVE ES IGUAL A LA ANTERIOR");
                    else {
                        cliente.setClave(claveNueva);
                        controlador.actualizaDatosCliente(cliente, cliente);
                        cliente.setClave(claveNueva);
                        System.out.println(" - SU CLAVE HA SIDO CAMBIADA CON ÉXITO: " + claveNueva);
                    }
                }
            }
            if (opcion == 9) Utils.mensajeCierraPrograma();
        } while (opcion != 9);
        controlador.guardaClientes();
    }

    //Metodo que se encarga de pintar los pedidos hechos por el cliente
    private static void pintaPedidosCliente(Cliente cliente) {
        if (cliente == null) System.out.println("\n * ERROR: HA OCURRIDO UN ERROR");

        if (cliente.getPedidos().isEmpty())
            System.out.println(" * ERROR: Actualmente no tienes ningún pedido efectuado");

        for (Pedido p : cliente.getPedidos()) {
            if (p != null) System.out.println(p);
        }
    }

    //Metodo que busca productos por descripcion - modelo - marca
    private static void buscaProductoTermino(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca el TERMINO a buscar: ");
        String termino = S.nextLine();
        ArrayList<Producto> productosCoincideTermino = controlador.buscaProductosByTermino(termino);
        if (!productosCoincideTermino.isEmpty()) {
            pintaListaProductos(productosCoincideTermino);
            Utils.pulsaParaContinuar();
        } else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo que busca productos entre un rango de precio
    private static void buscaProductoPrecio(Controlador controlador) {
        boolean correcto = false;
        float precioMin = 0, precioMax = 0;
        do {
            System.out.println();
            System.out.println(" * VAMOS A BUSCAR PRODUCTOS ENTRE UN RANGO DE PRECIOS: ");
            try {
                System.out.print(" - Introduzca precio mínimo del producto a buscar: ");
                precioMin = Float.parseFloat(S.nextLine());
                System.out.print(" - Introduzca precio máximo del producto a buscar: ");
                precioMax = Float.parseFloat(S.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" * ERROR AL INTRODUCIR EL PRECIO");
            }

            if (precioMin < 0 || precioMax < 0) System.out.println(" * ERROR: Los precios no pueden ser negativos.");
            if (precioMin >= precioMax)
                System.out.println(" * ERROR AL INTRODUCIR LOS PRECIOS: PRECIO MINIMO NO PUEDE SER MAYOR QUE PRECIO MAXIMO");
            if (precioMin < precioMax) correcto = true;

        } while (!correcto);

        ArrayList<Producto> productosCoincidePrecio = controlador.buscaProductosByPrecio(precioMin, precioMax);
        if (!productosCoincidePrecio.isEmpty()) {
            pintaListaProductos(productosCoincidePrecio);
            Utils.pulsaParaContinuar();
        } else System.out.println("No se han encontrado coincidencias");

    }

    //Metodo que busca productos por descripcion
    private static void buscaProductoDescripcion(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca la DESCRIPCIÓN del producto a buscar: ");
        String descripcion = S.nextLine();
        ArrayList<Producto> productosCoincideDescripcion = controlador.buscaProductosByDescripcion(descripcion);
        if (!productosCoincideDescripcion.isEmpty()) {
            pintaListaProductos(productosCoincideDescripcion);
            Utils.pulsaParaContinuar();
        } else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo que busca productos por modelo
    private static void buscaProductoModelo(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca el MODELO del producto a buscar: ");
        String modelo = S.nextLine();
        ArrayList<Producto> productosCoincideModelo = controlador.buscaProductosByModelo(modelo);
        if (!productosCoincideModelo.isEmpty()) {
            pintaListaProductos(productosCoincideModelo);
            Utils.pulsaParaContinuar();
        } else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo de la opcion para buscar productos por su marca
    private static void buscaProductoMarca(Controlador controlador) {
        System.out.println();
        System.out.print(" - Introduzca la MARCA del producto a buscar: ");
        String marca = S.nextLine();
        ArrayList<Producto> productosCoincideMarca = controlador.buscaProductosByMarca(marca);
        if (!productosCoincideMarca.isEmpty()) {
            pintaListaProductos(productosCoincideMarca);
            Utils.pulsaParaContinuar();
        } else System.out.println("No se han encontrado coincidencias");
    }

    //Metodo que pinta una lista de productos que le pasemos
    public static void pintaListaProductos(ArrayList<Producto> productos) {
        for (Producto p : productos) {
            if (p != null) System.out.println(p.pintaProductoADetalle());
        }
    }

    //Metodo que pinta una lista de productos que le pasemos
    public static String pintaListaProductosTelegram(ArrayList<Producto> productos) {
        String listaProductos = "";
        for (Producto p : productos) {
            if (p != null) listaProductos += p;
        }
        return listaProductos;
    }
}

