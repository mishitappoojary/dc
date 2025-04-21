import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Map<String, PrintWriter> clientWriters = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Group Chat Server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Receive client's username
                clientName = in.readLine();

                synchronized (clientWriters) {
                    clientWriters.put(clientName, out);
                    broadcastMessage("Server", clientName + " has joined the chat.");
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(clientName + ": " + message);
                    broadcastMessage(clientName, message);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
                synchronized (clientWriters) {
                    clientWriters.remove(clientName);
                    broadcastMessage("Server", clientName + " has left the chat.");
                }
            }
        }

        private void broadcastMessage(String sender, String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println(sender + ": " + message);
                }
            }
        }
    }
}
