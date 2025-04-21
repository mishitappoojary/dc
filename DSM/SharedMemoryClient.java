import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SharedMemoryClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 5000);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to the server.");

            String input;
            while (true) {
                System.out.print("Enter command (get/set <value>/exit): ");
                input = scanner.nextLine();
                out.println(input);

                String response = in.readLine();
                System.out.println("Server: " + response);

                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
