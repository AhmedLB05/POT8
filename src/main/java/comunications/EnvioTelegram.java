package comunications;

import models.Pedido;
import models.Trabajador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static view.Main.pintaListaProductosTelegram;

public class EnvioTelegram {
    public static void main(String[] args) {
        /*Trabajador t = new Trabajador(123, "Juan", "123", "ahmed@gmail.com", 672838292);
        Pedido p = new Pedido(
                101, // ID del pedido
                LocalDate.of(2023, 10, 20), // Fecha del pedido
                LocalDate.of(2023, 10, 25), // Fecha de entrega estimada
                null, // Fecha de entrega real (aún no entregado)
                1, // Estado del pedido (1 = En proceso)
                "El cliente solicita entrega antes de las 14:00.", // Comentario
                new ArrayList<>() {{
                    add(new Producto(1, "HP", "Pavilion", "Laptop Gamer con tarjeta gráfica dedicada", 1200.0f, 5));
                    add(new Producto(2, "Logitech", "M185", "Mouse inalámbrico ergonómico", 25.0f, 4));
                    add(new Producto(3, "Corsair", "K95 RGB", "Teclado mecánico con retroiluminación RGB", 80.0f, 5));
                }} // Lista de productos
        );
        enviaMensajeTrabajadorPedidoAsignado(t, p);*/
    }

    static boolean enviaMensajeTelegram(String mensaje) {
        String direccion; // URL de la API de mi bot en mi conversación
        String fijo = "https://api.telegram.org/bot7780416856:AAFf5vqnxNPuL-wYwrLuT4tD2C9PAnG69Zs/sendMessage?chat_id=1423538108&text=";
        try {
            // Codificar el mensaje para que sea válido en una URL
            String mensajeCodificado = URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
            direccion = fijo + mensajeCodificado; // Metemos el mensaje codificado al final
            URL url = new URL(direccion); // Creando un objeto URL con la dirección de la API de mi bot
            URLConnection con = url.openConnection(); // Realizando la petición GET
            // Con esto, copiamos en in la respuesta HTTP, por si lo necesitamos
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return true; // Ha tenido éxito
        } catch (IOException e) {
            System.out.println("Error al enviar el mensaje: " + e.getMessage());
            return false; // Fallo al enviar el mensaje
        }
    }

    public static void enviaMensajeTrabajadorPedidoAsignado(Trabajador t, Pedido p) {
        String mensajeEnviar = "📦 Asignación de Pedido 📦\n" +
                "\n" +
                "¡Hola, " + t.getNombre() + "! 👋\n" +
                "\n" +
                "Se te ha asignado un nuevo pedido. A continuación, los detalles:\n" +
                "\n" +
                "🔢 ID del Pedido: " + p.getId() + "\n" +
                "📅 Fecha del Pedido: " + p.getFechaPedido() + "\n" +
                "📅 Fecha de Entrega Estimada: " + p.getFechaEntregaEstimada() + "\n" +
                "🟢 Estado del Pedido: " + p.devuelveEstado(p.getEstado()) + "\n" +
                "📝 Comentarios: " + p.getComentario() + "\n" +
                "\n" +
                "📦 Productos:\n" +
                pintaListaProductosTelegram(p.getProductos()) + "\n" +
                "\n" +
                "Por favor, revisa la información y procede según lo planificado.\n" +
                "\n" +
                "Gracias por tu atención. 🙌";
        enviaMensajeTelegram(mensajeEnviar);
    }
}