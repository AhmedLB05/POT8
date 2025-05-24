package comunications;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import models.*;
import persistencia.Persistencia;
import utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EnvioMail {

    private static final String HOST = "smtp.gmail.com";  // Servidor SMTP de Gmail
    private static final String USER = "amfernanshop@gmail.com";  // Tu correo electrónico
    private static final String PASS = "vwqf kinf oaap cedq";  // Tu contraseña (o una contraseña de aplicación si usas Gmail)
    private static final int PORT = 587;


    //Metodo principal para probar el envío
    public static void main(String[] args) {
        String destinatario = "ahmedlb26205@gmail.com";  // Dirección de destino
        int num = Utils.generaTokenRegistro();

        // Llamamos al metodo para enviar el correo
        enviaTokenRegistro(destinatario, num);
    }

    //Metodo para enviar un token y verificar una cuenta
    public static boolean enviaTokenRegistro(String destino, int token) {

        String asunto = "VERIFICACIÓN FERNANSHOP";
        String mensajeHTML = getMensajeHTML(token);

        // Configuración de propiedades SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);

        // Crear la sesión SMTP
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            // Crear el mensaje MIME
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            message.setSubject(asunto);

            // Establecer el contenido del mensaje en formato HTML
            message.setContent(mensajeHTML, "text/html");

            // Enviar el correo
            Transport.send(message);
            System.out.println("Correo enviado con éxito!");
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo: " + e.getMessage());
        }
        return false;
    }

    //Metodo que guarda el html del token
    private static String getMensajeHTML(int token) {
        String numAleatorio = String.valueOf(token);

        return String.format("<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Código de Verificación</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #f4f7fa;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            padding: 30px;\n" +
                "            text-align: center;\n" +
                "            border-radius: 10px 10px 0 0;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 28px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .email-content {\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 0 0 10px 10px;\n" +
                "            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .verification-code {\n" +
                "            display: inline-block;\n" +
                "            padding: 20px 40px;\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            font-size: 32px;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 10px;\n" +
                "            margin: 20px 0;\n" +
                "            text-align: center;\n" +
                "            width: 100%%;\n" +
                "            max-width: 300px;\n" +
                "            margin-left: auto;\n" +
                "            margin-right: auto;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 30px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .footer a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h1>Bienvenido a nuestro servicio</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <p>Gracias por registrarte en nuestro servicio. Estamos emocionados de tenerte con nosotros.</p>\n" +
                "            <p>Para completar tu registro y verificar tu cuenta, por favor usa el siguiente código de verificación:</p>\n" +
                "            <div class=\"verification-code\">%s</div>\n" +
                "            <p>Este código es válido por 10 minutos. Si no solicitaste este código, ignora este mensaje.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 PRACTICA OBLIGATORIA AHMED LB. Todos los derechos reservados.</p>\n" +
                "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>", numAleatorio);
    }

    //Metodo que envia un correo al trabajador que se le ha asignado un pedido
    public static void enviaCorreoPedido(Trabajador t, PedidoClienteDataClass pedidoCliente, String asunto) {
        // Formateador para las fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Contenido HTML del correo
        String mensaje = "<!DOCTYPE html>\n" + "<html lang=\"es\">\n" + "<head>\n" + "    <meta charset=\"UTF-8\">\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "    <title>Asignación de Pedido</title>\n" + "    <style>\n" + "        body {\n" + "            font-family: 'Arial', sans-serif;\n" + "            background-color: #f0f4f8;\n" + "            color: #333;\n" + "            margin: 0;\n" + "            padding: 0;\n" + "            display: flex;\n" + "            justify-content: center;\n" + "            align-items: center;\n" + "            height: 100vh;\n" + "        }\n" + "        .container {\n" + "            width: 100%;\n" + "            max-width: 650px;\n" + "            margin: 0 auto;\n" + "            padding: 20px;\n" + "            border-radius: 10px;\n" + "            background-color: #fff;\n" + "            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);\n" + "        }\n" + "        .email-header {\n" + "            background-color: #007BFF;\n" + "            color: white;\n" + "            padding: 30px;\n" + "            text-align: center;\n" + "            border-radius: 10px 10px 0 0;\n" + "        }\n" + "        .email-header h1 {\n" + "            margin: 0;\n" + "            font-size: 26px;\n" + "            font-weight: bold;\n" + "        }\n" + "        .email-content {\n" + "            padding: 20px;\n" + "            color: #555;\n" + "            font-size: 16px;\n" + "            line-height: 1.6;\n" + "            margin-top: 10px;\n" + "        }\n" + "        .pedido-details {\n" + "            background-color: #f7f9fc;\n" + "            padding: 20px;\n" + "            border-radius: 10px;\n" + "            margin: 20px 0;\n" + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);\n" + "        }\n" + "        .pedido-details h2 {\n" + "            margin-top: 0;\n" + "            font-size: 22px;\n" + "            color: #007BFF;\n" + "        }\n" + "        .pedido-details p {\n" + "            margin: 10px 0;\n" + "            font-size: 16px;\n" + "            color: #333;\n" + "        }\n" + "        .pedido-details ul {\n" + "            list-style-type: none;\n" + "            padding: 0;\n" + "        }\n" + "        .pedido-details li {\n" + "            padding: 5px 0;\n" + "            font-size: 16px;\n" + "        }\n" + "        .footer {\n" + "            text-align: center;\n" + "            margin-top: 30px;\n" + "            font-size: 14px;\n" + "            color: #888;\n" + "        }\n" + "        .footer a {\n" + "            color: #007BFF;\n" + "            text-decoration: none;\n" + "        }\n" + "        .footer a:hover {\n" + "            text-decoration: underline;\n" + "        }\n" + "    </style>\n" + "</head>\n" + "<body>\n" + "    <div class=\"container\">\n" + "        <div class=\"email-header\">\n" + "            <h1>¡Hola, " + t.getNombre() + "!</h1>\n" + "        </div>\n" + "        <div class=\"email-content\">\n" + "            <p>Se te ha asignado un nuevo pedido. A continuación, los detalles:</p>\n" + "            <div class=\"pedido-details\">\n" + "                <h2>Detalles del Pedido</h2>\n" + "                <p><strong>ID del Pedido:</strong> " + pedidoCliente.getIdPedido() + "</p>\n" + "                <p><strong>Fecha del Pedido:</strong> " + pedidoCliente.getFechaPedido().format(formatter) + "</p>\n" + "                <p><strong>Fecha de Entrega Estimada:</strong> " + pedidoCliente.getFechaEntregaEstimada().format(formatter) + "</p>\n" + "                <p><strong>Estado:</strong> " + pedidoCliente.devuelveEstado(pedidoCliente.getEstado()) + "</p>\n" + "                <p><strong>Comentarios:</strong> " + pedidoCliente.getComentario() + "</p>\n" + "                <p><strong>Cliente:</strong></p>\n" + "                <ul>\n" + "                    <li><strong>Nombre:</strong> " + pedidoCliente.getNombre() + "</li>\n" + "                    <li><strong>Email:</strong> " + pedidoCliente.getEmail() + "</li>\n" + "                    <li><strong>Teléfono:</strong> " + pedidoCliente.getMovil() + "</li>\n" + "                    <li><strong>Dirección:</strong> " + pedidoCliente.getDireccion() + ", " + pedidoCliente.getLocalidad() + ", " + pedidoCliente.getProvincia() + "</li>\n" + "                </ul>\n" + "                <p><strong>Productos:</strong></p>\n" + "                <ul>\n";

        // Añadir los productos al mensaje
        for (Producto producto : pedidoCliente.getProductos()) {
            mensaje += "<li>" + producto + "</li>\n";
        }

        mensaje += "                </ul>\n" + "            </div>\n" + "            <p>Por favor, revisa la información y procede según lo planificado.</p>\n" + "        </div>\n" + "        <div class=\"footer\">\n" + "            <p>&copy; 2025 FERNANSHOP. Todos los derechos reservados.</p>\n" + "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" + "        </div>\n" + "    </div>\n" + "</body>\n" + "</html>";

        // Configuración del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");  // Puerto para TLS

        // Configuración de la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(t.getEmail()));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito al trabajador");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo.");
        }
    }

    //Metodo que le envia un correo a un cliente y le indica que el estado del pedido se ha modificado
    public static void enviaCorreoPedidoEstado(Cliente c, PedidoClienteDataClass p) {
        // Formateador para las fechas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Asunto del correo
        String asunto = "Actualización del Estado de tu Pedido";

        // Contenido HTML del correo
        String mensaje = "<!DOCTYPE html>\n" + "<html lang=\"es\">\n" + "<head>\n" + "    <meta charset=\"UTF-8\">\n" + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "    <title>Actualización del Estado del Pedido</title>\n" + "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #f0f4f8;\n" +
                "            color: #333;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 650px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            background-color: #fff;\n" +
                "            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .email-header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: white;\n" +
                "            padding: 30px;\n" +
                "            text-align: center;\n" +
                "            border-radius: 10px 10px 0 0;\n" +
                "        }\n" +
                "        .email-header h1 {\n" +
                "            margin: 0;\n" +
                "            font-size: 28px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .email-content {\n" +
                "            padding: 20px;\n" +
                "            color: #555;\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.6;\n" +
                "            margin-top: 10px;\n" +
                "        }\n" +
                "        .pedido-details {\n" +
                "            background-color: #f7f9fc;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 10px;\n" +
                "            margin: 20px 0;\n" +
                "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);\n" +
                "        }\n" +
                "        .pedido-details h2 {\n" +
                "            margin-top: 0;\n" +
                "            font-size: 24px;\n" +
                "            color: #007BFF;\n" +
                "        }\n" +
                "        .pedido-details p {\n" +
                "            margin: 10px 0;\n" +
                "            font-size: 16px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .pedido-details ul {\n" +
                "            list-style-type: none;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .pedido-details li {\n" +
                "            padding: 5px 0;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 30px;\n" +
                "            font-size: 14px;\n" +
                "            color: #888;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        .footer a:hover {\n" +
                "            text-decoration: underline;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"email-header\">\n" +
                "            <h1>¡Hola, " + c.getNombre() + "!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"email-content\">\n" +
                "            <p>El estado de tu pedido ha sido actualizado. A continuación, los detalles:</p>\n" +
                "            <div class=\"pedido-details\">\n" +
                "                <h2>Detalles del Pedido</h2>\n" +
                "                <p><strong>ID del Pedido:</strong> " + p.getIdPedido() + "</p>\n" +
                "                <p><strong>Fecha del Pedido:</strong> " + p.getFechaPedido().format(formatter) + "</p>\n" +
                "                <p><strong>Fecha de Entrega Estimada:</strong> " + p.getFechaEntregaEstimada().format(formatter) + "</p>\n" +
                "                <p><strong>Nuevo Estado:</strong> " + p.devuelveEstado(p.getEstado()) + "</p>\n" +
                "                <p><strong>Comentarios:</strong> " + p.getComentario() + "</p>\n" +
                "                <p><strong>Productos:</strong></p>\n" +
                "                <ul>\n";

        // Añadir los productos al mensaje
        for (Producto producto : p.getProductos()) {
            mensaje += "                    <li>" + producto.getMarca() + " " + producto.getModelo() + " - " + producto.getDescripcion() + "</li>\n";
        }

        mensaje += "                </ul>\n" +
                "            </div>\n" +
                "            <p>Gracias por confiar en nosotros. Si tienes alguna pregunta, no dudes en contactarnos.</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2025 FERNANSHOP. Todos los derechos reservados.</p>\n" +
                "            <p><a href=\"#\">Política de privacidad</a> | <a href=\"#\">Términos de servicio</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        // Configuración del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");  // Puerto para TLS

        // Configuración de la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(c.getEmail()));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito a " + c.getNombre() + " (" + c.getEmail() + ")");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Hubo un error al enviar el correo.");
        }
    }

    //Metodo que envia el resumen del pedido en formato PDF
    public static void enviaCorreoResumen(String destino, Pedido pedidoTemp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(USER));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mensaje.setSubject("Resumen del pedido");

            // Crear la parte del mensaje con HTML
            MimeBodyPart cuerpoTexto = new MimeBodyPart();
            String htmlContent = "<html>" + "<body style='font-family: Arial, sans-serif; color: #333;'>" + "<h2 style='color: #007BFF;'>¡Hola!</h2>" + "<p>Gracias por realizar tu compra con nosotros. A continuación encontrarás el resumen detallado de tu pedido.</p>" + "<p>Si tienes alguna duda o necesitas más información, no dudes en <a href='mailto:amfernanshop@gmail.com' style='color: #007BFF;'>contactarnos</a>.</p>" + "<br>" + "<p>Un cordial saludo,</p>" + "<p><strong>El equipo de FERNANSHOP</strong></p>" + "</body>" + "</html>";

            // Establecer el contenido como HTML
            cuerpoTexto.setContent(htmlContent, "text/html");

            MimeBodyPart adjunto = new MimeBodyPart();
            File archivo = new File(Persistencia.getRutaPDF() + "\\" + pedidoTemp.getId() + ".pdf");
            if (!archivo.exists()) {
                throw new FileNotFoundException("No se encontró el archivo en la ruta: " + Persistencia.getRutaPDF() + "\\" + pedidoTemp.getId() + ".pdf");
            }
            adjunto.attachFile(archivo);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoTexto);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            Transport.send(mensaje);

        } catch (Exception e) {
            System.out.println("El correo introducido no es válido");
            e.printStackTrace();
        }
    }

    //Metodo que envia el listado de todos los pedidos en formato Excel
    public static void enviarExcelCorreo(String destino) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASS);
            }
        });

        try {
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(USER));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
            mensaje.setSubject("📋 Listado de pedidos");

            MimeBodyPart cuerpoTexto = new MimeBodyPart();
            String html = """
                    <html>
                        <body style="font-family: Arial, sans-serif; color: #333;">
                            <h3>Hola,</h3>
                            <p>Adjunto te envío el archivo Excel con el resumen de los pedidos.</p>
                            <p>Si necesitas algo más, no dudes en responder a este correo.</p>
                            <br>
                            <p>Un saludo,<br><strong>El equipo de FernanShop</strong></p>
                        </body>
                    </html>
                    """;
            cuerpoTexto.setContent(html, "text/html; charset=utf-8");

            MimeBodyPart adjunto = new MimeBodyPart();
            File archivo = new File(Persistencia.getRutaExcel() + File.separator + "Pedidos.xlsx");
            if (!archivo.exists()) {
                throw new FileNotFoundException("No se encontró el archivo: " + archivo.getAbsolutePath());
            }
            adjunto.attachFile(archivo);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoTexto);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            Transport.send(mensaje);
        } catch (Exception e) {
            System.out.println("❌ Error al enviar el correo:");
            e.printStackTrace();
        }
    }


}
