package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Utils {
    public static final Scanner S = new Scanner(System.in);
    public static final int IVA = 21;

    //Metodo pulsar para continuar
    public static void pulsaParaContinuar() {
        System.out.println();
        System.out.print("Pulse para continuar...");
        S.nextLine();
    }

    //Mensaje que cierra un programa
    public static void mensajeCierraPrograma() {
        System.out.print("Saliendo");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 3; i++) {
            System.out.print(" .");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    public static String formateaFechaLog(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy\\HH:mm:ss");
        return now.format(formatter);
    }

    public static String formateaFecha(LocalDate fechaPedido) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaPedido.format(formatter);
    }

    // Metodo para generar un número aleatorio de 6 dígitos
    public static int generaTokenRegistro() {
        Random random = new Random();
        int numAleatorio = 100000 + random.nextInt(900000);
        return numAleatorio;
    }

    //Metodo que nos escribe un mensaje segun el boolean del guardado en persistencia
    public static void mensajeGuardadoPersistencia(boolean guardado) {
        if (guardado) System.out.println("\n - Se han guardado con éxito todos los datos\n");
        if (!guardado) System.out.println("\n - Oh oh! Ha ocurrido un error al guardar los datos\n");
    }
}
