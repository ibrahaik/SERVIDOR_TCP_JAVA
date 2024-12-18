import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.in;


public class ServidorTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Servidor TCP iniciado");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                new Thread(() -> manejarCliente(clienteSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(Socket clienteSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true)) {

            String mensajeCliente;
            // Bucle para recibir y responder al cliente
            while ((mensajeCliente = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensajeCliente);
                out.println("Echo: " + mensajeCliente);  // Responde con el mensaje recibido
            }

            // Cerramos la conexión con el cliente
            clienteSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
