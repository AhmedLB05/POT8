package persistencia;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import controller.Controlador;
import models.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Utils;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

public class Persistencia {

    public static final String RUTA_PROPERTIES = "./config/config.properties";

    //Metodo que lee los clientes de la persistencia
    public static ArrayList<Cliente> leeClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        File directorioCliente = new File(getRutaClientes());
        if (directorioCliente.exists()) {
            for (String nombreFichero : directorioCliente.list()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioCliente + "/" + nombreFichero));
                    clientes.add((Cliente) ois.readObject());
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    return clientes;
                }
            }
        }
        return clientes;
    }

    //Metodo que lee los trabajadores de la persistencia
    public static ArrayList<Trabajador> leeTrabajadores() {
        ArrayList<Trabajador> trabajadores = new ArrayList<>();
        File directorioTrabajadores = new File(getRutaTrabajadores());
        if (directorioTrabajadores.exists()) {
            for (String nombreFichero : directorioTrabajadores.list()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioTrabajadores + "/" + nombreFichero));
                    trabajadores.add((Trabajador) ois.readObject());
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    return trabajadores;
                }
            }
        }
        return trabajadores;
    }

    //Metodo que lee los admins de la persistencia
    public static ArrayList<Admin> leeAdmin() {
        ArrayList<Admin> admins = new ArrayList<>();
        File directorioAdmins = new File(getRutaAdmin());
        if (directorioAdmins.exists()) {
            for (String nombreFichero : directorioAdmins.list()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioAdmins + "/" + nombreFichero));
                    admins.add((Admin) ois.readObject());
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    return admins;
                }
            }
        }
        return admins;
    }

    //Metodo que lee los productos de la persistencia
    public static ArrayList<Producto> leeProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        File directorioProductos = new File(getRutaProductos());
        if (directorioProductos.exists()) {
            for (String nombreFichero : directorioProductos.list()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioProductos + "/" + nombreFichero));
                    productos.add((Producto) ois.readObject());
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    return productos;
                }
            }
        }
        return productos;
    }

    //Metodo que guarda en disco a los clientes
    public static boolean guardaClientesPersistencia(ArrayList<Cliente> clientesGuardado) {
        File directorioCliente = new File(getRutaClientes());

        if (!directorioCliente.exists()) directorioCliente.mkdirs();
        for (Cliente c : clientesGuardado) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(directorioCliente + "/" + c.getId() + ".cliente"));
                oos.writeObject(c);
                oos.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    //Metodo que guarda el array de trabajadores en disco
    public static boolean guardaTrabajadoresPersistencia(ArrayList<Trabajador> trabajadoresGuardado) {
        File directorioTrabajadores = new File(getRutaTrabajadores());
        if (!directorioTrabajadores.exists()) directorioTrabajadores.mkdirs();
        for (Trabajador t : trabajadoresGuardado) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(directorioTrabajadores + "/" + t.getId() + ".trabajadores"));
                oos.writeObject(t);
                oos.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    //Metodo que guarda en disco a los admins
    public static boolean guardaAdminsPersistencia(ArrayList<Admin> adminsGuardado) {
        File directorioAdmins = new File(getRutaAdmin());

        if (!directorioAdmins.exists()) directorioAdmins.mkdirs();
        for (Admin a : adminsGuardado) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(directorioAdmins + "/" + a.getId() + ".admin"));
                oos.writeObject(a);
                oos.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    //Metodo que guarda en disco a los clientes
    public static boolean guardaProductosPersistencia(ArrayList<Producto> catalogoGuardado) {
        File directorioProductos = new File(getRutaProductos());

        if (!directorioProductos.exists()) directorioProductos.mkdirs();
        for (Producto p : catalogoGuardado) {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(directorioProductos + "/" + p.getId() + ".producto"));
                oos.writeObject(p);
                oos.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    // Metodo que guarda los inicio de sesion de los usuarios en el log
    public static void inicioSesionLog(Object user) {
        File directorioLog = new File(getRutaLog());
        if (!directorioLog.exists()) directorioLog.mkdirs();
        if (user instanceof Admin) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Inicio de sesión;" + (((Admin) user).getNombre()) + ";Administrador;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                bw.close();
            } catch (IOException e) {
                return;
            }
        }
        if (user instanceof Trabajador) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Inicio de sesión;" + (((Trabajador) user).getNombre()) + ";Trabajador;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                bw.close();
            } catch (IOException e) {
                return;
            }
        }
        if (user instanceof Cliente) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Inicio de sesión;" + (((Cliente) user).getNombre()) + ";Cliente;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                bw.close();
            } catch (IOException e) {
                return;
            }
        }
    }

    //Metodo que guarda los nuevos pedidos en un log
    public static void guardaNuevoPedido(int idCliente, int idTrabajador) {
        File directorioLog = new File(getRutaLog());
        if (!directorioLog.exists()) directorioLog.mkdirs();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
            bw.write("Nuevo pedido;" + idCliente + ";" + idTrabajador + ";" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
            bw.close();
        } catch (IOException e) {
            return;
        }
    }

    //Metodo que guarda los actualiza pedido en un log
    public static void guardaActualizaPedido(Pedido pedido) {
        File directorioLog = new File(getRutaLog());
        if (!directorioLog.exists()) directorioLog.mkdirs();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
            bw.write("Actualiza pedido;" + pedido.getId() + ";" + pedido.devuelveEstado(pedido.getEstado()) + ";" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
            bw.close();
        } catch (IOException e) {
            return;
        }
    }

    //Metodo que guarda los cierres de sesion de los usuarios en el log
    public static void guardaCierreSesion(Object user) {
        File directorioLog = new File(getRutaLog());

        if (!directorioLog.exists()) directorioLog.mkdirs();

        if (user instanceof Admin) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Cierre sesión;" + ((Admin) user).getNombre() + ";Administrador;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                ultimoCierreSesionUsuario(((Admin) user).getId());
                bw.close();
            } catch (IOException e) {
                return;
            }
        }

        if (user instanceof Trabajador) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Cierre sesión;" + ((Trabajador) user).getNombre() + ";Trabajador;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                ultimoCierreSesionUsuario(((Trabajador) user).getId());
                bw.close();
            } catch (IOException e) {
                return;
            }
        }

        if (user instanceof Cliente) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(directorioLog + "\\" + "log", true));
                bw.write("Cierre sesión;" + ((Cliente) user).getNombre() + ";Cliente;" + Utils.formateaFechaLog(LocalDateTime.now()) + "\n");
                ultimoCierreSesionUsuario(((Cliente) user).getId());
                bw.close();
            } catch (IOException e) {
                return;
            }
        }

    }

    private static void ultimoCierreSesionUsuario(int idUsuario) {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader(RUTA_PROPERTIES)));
            prop.setProperty(String.valueOf(idUsuario), Utils.formateaFechaLog(LocalDateTime.now()));
            prop.store(new FileOutputStream(RUTA_PROPERTIES), "Ultimo inicio sesion ID: " + idUsuario + " el " + Utils.formateaFecha(LocalDate.now()));
        } catch (IOException e) {
            return;
        }
    }

    // Metodo que devuelve el ultimo inicio de sesion del usuario (busca en el properties)
    public static String ultimoInicioSesion(int idUsuario) {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader(RUTA_PROPERTIES)));
            return prop.getProperty(String.valueOf(idUsuario));
        } catch (IOException e) {
            return "";
        }
    }

    //Metodo que devuelve si hay acceso de invitados
    public static boolean getAccesoInvitados() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return Boolean.parseBoolean(prop.getProperty("ACCESO_INVITADO", "false"));
        } catch (IOException e) {
            return false;
        }
    }

    //Metodo que recoge la ruta de persistencia de los clientes desde Properties
    private static String getRutaLog() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_LOG", "data/log");
        } catch (IOException e) {
            return "";
        }
    }


    //Metodo que recoge la ruta de persistencia de los clientes desde Properties
    private static String getRutaClientes() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_CLIENTES", "data/Clientes");
        } catch (IOException e) {
            return "";
        }
    }

    //Metodo que recoge la ruta de persistencia de los trabajadores desde Properties
    private static String getRutaTrabajadores() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_TRABAJADORES", "data/Trabajadores");
        } catch (IOException e) {
            return "";
        }
    }

    //Metodo que recoge la ruta de persistencia de los admins desde Properties
    private static String getRutaAdmin() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_ADMIN", "data/Admin");
        } catch (IOException e) {
            return "";
        }
    }

    //Metodo que recoge la ruta de persistencia de los admins desde Properties
    private static String getRutaProductos() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_PRODUCTOS", "data/Productos");
        } catch (IOException e) {
            return "";
        }
    }

    public static String getRutaPDF() {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader(RUTA_PROPERTIES)));
            return prop.getProperty("RUTA_PDF", "data/documentos/PDF");
        } catch (IOException e) {
            return "";
        }
    }

    public static String getRutaExcel() {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader(RUTA_PROPERTIES)));
            return prop.getProperty("RUTA_EXCEL", "data/documentos/EXCEL");
        } catch (IOException e) {
            return "";
        }
    }

    private static String getRutaBackup() {
        Properties prop = new Properties();
        try {
            BufferedReader br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            prop.load(br);
            return prop.getProperty("RUTA_BACKUP", "data/backup");
        } catch (IOException e) {
            return "";
        }
    }


    public static boolean eliminaTrabajador(Trabajador trabajadorBaja) {
        File rutaTrabajador = new File(getRutaTrabajadores() + "/" + trabajadorBaja.getId() + ".trabajadores");
        if (rutaTrabajador.exists()) {
            try {
                return Files.deleteIfExists(rutaTrabajador.toPath());
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static ArrayList<String> configuracionPrograma() {
        ArrayList<String> config = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(RUTA_PROPERTIES));
            String linea = "";
            while (linea != null) {
                linea = br.readLine();
                if (linea != null) config.add(linea);
            }
            br.close();
        } catch (IOException e) {
            return config;
        }
        return config;
    }

    public static boolean creaBackup(Controlador controlador) {
        File directorioBackup = new File(getRutaBackup());
        if (!directorioBackup.exists()) directorioBackup.mkdirs();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directorioBackup + "\\" + "controller.backup"));
            oos.writeObject(controlador);
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean creaBackupPersonalizado(String ruta, Controlador controlador) {
        File directorioBackup = new File(ruta);
        if (!directorioBackup.exists()) directorioBackup.mkdirs();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directorioBackup + "\\" + "controller.backup"));
            oos.writeObject(controlador);
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Controlador recuperaBackup() {
        Controlador backup;
        File directorioBackup = new File(getRutaBackup());
        if (!directorioBackup.exists()) return null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioBackup + "\\" + "controller.backup"));
            backup = (Controlador) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return backup;
    }

    public static Controlador recuperaBackupPersonalizado(String ruta) {
        Controlador backup;
        File directorioBackup = new File(ruta);
        if (!directorioBackup.exists()) return null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directorioBackup + "\\" + "controller.backup"));
            backup = (Controlador) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return backup;
    }

    public static void guardaResumenPedido(Pedido pedidoTemp) {
        File directorioDocumentos = new File(getRutaPDF());
        if (!directorioDocumentos.exists()) directorioDocumentos.mkdirs();

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            String nombreArchivo = pedidoTemp.getId() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(directorioDocumentos, nombreArchivo)));
            document.open();

            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE);
            Font seccionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
            Font textoFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Font textoNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);

            Paragraph titulo = new Paragraph("🚚 RESUMEN DE PEDIDO 🛍️", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);

            PdfPCell tituloCell = new PdfPCell(new Phrase(titulo));
            tituloCell.setBackgroundColor(new BaseColor(0, 123, 255));
            tituloCell.setBorder(Rectangle.NO_BORDER);
            tituloCell.setPadding(10);
            document.add(tituloCell);

            document.add(new Paragraph("ID del pedido: " + pedidoTemp.getId(), textoNegrita));
            document.add(new Paragraph("Fecha de pedido: " + Utils.formateaFecha(pedidoTemp.getFechaPedido()), textoFont));
            document.add(new Paragraph("Fecha de entrega estimada: " + Utils.formateaFecha(pedidoTemp.getFechaEntregaEstimada()), textoFont));
            document.add(new Paragraph("Estado: " + pedidoTemp.devuelveEstado(pedidoTemp.getEstado()), textoFont));
            document.add(new Paragraph("Comentario: " + pedidoTemp.getComentario(), textoFont));

            document.add(new Paragraph(" "));
            document.add(createLineSeparator());
            document.add(new Paragraph(" "));

            Paragraph productosTitulo = new Paragraph("📦 Productos", seccionFont);
            productosTitulo.setSpacingBefore(15);
            productosTitulo.setSpacingAfter(10);

            PdfPCell productosCell = new PdfPCell(new Phrase(productosTitulo));
            productosCell.setBackgroundColor(new BaseColor(255, 193, 7));
            productosCell.setBorder(Rectangle.NO_BORDER);
            productosCell.setPadding(5);
            document.add(productosCell);

            Paragraph productosContenido = new Paragraph(pedidoTemp.pintaProductos(pedidoTemp.getProductos()), textoFont);
            productosContenido.setIndentationLeft(10);
            productosContenido.setSpacingAfter(15);
            productosContenido.setSpacingBefore(5);
            document.add(productosContenido);

            document.add(new Paragraph(" "));
            document.add(createLineSeparator());
            document.add(new Paragraph(" "));

            Paragraph totalesTitulo = new Paragraph("💰 Totales", seccionFont);
            totalesTitulo.setSpacingBefore(15);
            totalesTitulo.setSpacingAfter(10);

            PdfPCell totalesCell = new PdfPCell(new Phrase(totalesTitulo));
            totalesCell.setBackgroundColor(new BaseColor(40, 167, 69));
            totalesCell.setBorder(Rectangle.NO_BORDER);
            totalesCell.setPadding(5);
            document.add(totalesCell);

            document.add(new Paragraph("Subtotal (sin IVA): " + pedidoTemp.calculaTotalPedidoSinIVA() + " €", textoNegrita));
            document.add(new Paragraph("IVA (21%): " + pedidoTemp.calculaIVAPedido(21) + " €", textoNegrita));
            document.add(new Paragraph("Total (con IVA): " + pedidoTemp.calculaTotalPedidoConIVA(21) + " €", textoNegrita));

            document.add(new Paragraph(" "));
            document.add(createLineSeparator());
            document.add(new Paragraph(" "));

            document.close();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private static LineSeparator createLineSeparator() {
        LineSeparator line = new LineSeparator();
        line.setLineWidth(1f);
        line.setLineColor(BaseColor.BLACK);
        return line;
    }


    public static void adjuntaCorreos(ArrayList<Pedido> listado) {
        File carpetaDocumentos = new File(getRutaExcel());
        if (!carpetaDocumentos.exists()) carpetaDocumentos.mkdirs();

        String nombreArchivo = "Pedidos.xlsx";
        Workbook libro = new XSSFWorkbook();
        Sheet hoja = libro.createSheet("Pedidos");

        String[] encabezados = {"ID", "Fecha Pedido", "Fecha Entrega", "Estado", "Comentario", "Cantidad"};
        int indiceFila = 0;

        // Estilo para encabezados
        CellStyle estiloEncabezado = libro.createCellStyle();
        org.apache.poi.ss.usermodel.Font fuenteEncabezado = libro.createFont(); // <- Aquí la inicialización correcta
        fuenteEncabezado.setBold(true);
        fuenteEncabezado.setColor(IndexedColors.WHITE.getIndex());
        estiloEncabezado.setFont(fuenteEncabezado);
        estiloEncabezado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloEncabezado.setAlignment(HorizontalAlignment.CENTER);
        estiloEncabezado.setBorderBottom(BorderStyle.THIN);
        estiloEncabezado.setBorderTop(BorderStyle.THIN);
        estiloEncabezado.setBorderLeft(BorderStyle.THIN);
        estiloEncabezado.setBorderRight(BorderStyle.THIN);

        // Estilo para celdas normales
        CellStyle estiloCeldas = libro.createCellStyle();
        estiloCeldas.setBorderBottom(BorderStyle.THIN);
        estiloCeldas.setBorderTop(BorderStyle.THIN);
        estiloCeldas.setBorderLeft(BorderStyle.THIN);
        estiloCeldas.setBorderRight(BorderStyle.THIN);
        estiloCeldas.setVerticalAlignment(VerticalAlignment.CENTER);

        // Crear encabezados
        Row filaEncabezado = hoja.createRow(indiceFila++);
        for (int i = 0; i < encabezados.length; i++) {
            Cell celda = filaEncabezado.createCell(i);
            celda.setCellValue(encabezados[i]);
            celda.setCellStyle(estiloEncabezado);
        }

        // Rellenar datos
        for (Pedido pedido : listado) {
            Row fila = hoja.createRow(indiceFila++);
            Cell celda0 = fila.createCell(0);
            celda0.setCellValue(pedido.getId());
            celda0.setCellStyle(estiloCeldas);
            Cell celda1 = fila.createCell(1);
            celda1.setCellValue(Utils.formateaFecha(pedido.getFechaPedido()));
            celda1.setCellStyle(estiloCeldas);
            Cell celda2 = fila.createCell(2);
            celda2.setCellValue(Utils.formateaFecha(pedido.getFechaEntregaEstimada()));
            celda2.setCellStyle(estiloCeldas);
            Cell celda3 = fila.createCell(3);
            celda3.setCellValue(pedido.devuelveEstado(pedido.getEstado()));
            celda3.setCellStyle(estiloCeldas);
            Cell celda4 = fila.createCell(4);
            celda4.setCellValue(pedido.getComentario());
            celda4.setCellStyle(estiloCeldas);
            Cell celda5 = fila.createCell(5);
            celda5.setCellValue(pedido.getProductos().size());
            celda5.setCellStyle(estiloCeldas);
        }

        // Autoajustar columnas
        for (int i = 0; i < encabezados.length; i++) {
            hoja.autoSizeColumn(i);
        }

        // Guardar
        File archivo = new File(carpetaDocumentos, nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            libro.write(fos);
            libro.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
