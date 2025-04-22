import java.io.*;
import java.net.*;

public class SharedMemoryServer {
    private static int sharedVariable = 50;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected.");
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket client) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                if (input.equalsIgnoreCase("get")) {
                    out.println("Shared Variable: " + sharedVariable);
                } else if (input.startsWith("set")) {
                    try {
                        sharedVariable = Integer.parseInt(input.split(" ")[1]);
                        out.println("Updated to: " + sharedVariable);
                    } catch (Exception e) {
                        out.println("Invalid format. Use: set <number>");
                    }
                } else if (input.equalsIgnoreCase("exit")) {
                    out.println("Goodbye!");
                    break;
                } else {
                    out.println("Unknown command.");
                }
            }
            client.close();
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
