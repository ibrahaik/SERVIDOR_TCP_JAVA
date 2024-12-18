import java.io.*;
import java.net.*;

public class ClienteTCP {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 1234)) {
            // Flujos de entrada y salida para la comunicación
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)); // Para leer desde consola


            String mensajeCliente;
            String respuesta;

            // Bucle para enviar múltiples mensajes
            while (true) {
                System.out.print("Introduce un mensaje al servidor (o 'salir' para terminar): ");
                mensajeCliente = teclado.readLine();

                if (mensajeCliente.equalsIgnoreCase("salir")) {
                    break;  // Salir del bucle si el usuario escribe "salir"
                }

                // Enviar el mensaje al servidor
                out.println(mensajeCliente);

                // Leer la respuesta del servidor
                respuesta = in.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
            }

            // Cerramos la conexión
            socket.close();
            System.out.println("Conexión cerrada.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
