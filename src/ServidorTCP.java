import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class ServidorTCP {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            System.out.println("Servidor TCP iniciat, esperant connexions...");

            // El servidor escolta i atén múltiples clients alhora
            while (true) {
                // Aceptar una nova connexió
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connectat: " + clientSocket.getInetAddress());

                // Crear un fil per gestionar la connexió amb el client
                new Thread(() -> gestionarClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gestionarClient(Socket clientSocket) {
        try (
                ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            // Rebre l'objecte Llista del client
            Llista llistaRebuda = (Llista) objectIn.readObject();
            System.out.println("Rebut: " + llistaRebuda.getNom());

            // Ordenar la llista i eliminar els duplicats
            List<Integer> llistaOrdenada = llistaRebuda.getNumberList().stream()
                    .distinct()  // Eliminar duplicats
                    .sorted()    // Ordenar
                    .collect(Collectors.toList());

            // Establir la llista ordenada
            llistaRebuda.setNumberList(llistaOrdenada);

            // Enviar l'objecte modificat al client
            objectOut.writeObject(llistaRebuda);
            System.out.println("Resposta enviada al client.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
