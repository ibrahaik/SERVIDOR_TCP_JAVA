import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

public class ClientTCP {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5555)) {
            // Crear l'objecte Llista a enviar al servidor
            List<Integer> numbers = Arrays.asList(5, 3, 7, 3, 2, 8, 5, 9);
            Llista llista = new Llista("Llista de Números", numbers);

            // Crear ObjectOutputStream per enviar l'objecte al servidor
            ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectOut.writeObject(llista);
            System.out.println("Objecte enviat al servidor.");

            // Crear ObjectInputStream per rebre la resposta del servidor
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            Llista resposta = (Llista) objectIn.readObject();

            // Mostrar la resposta (llista ordenada i sense duplicats)
            System.out.println("Resposta del servidor:");
            System.out.println("Nom: " + resposta.getNom());
            System.out.println("Llista: " + resposta.getNumberList());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
