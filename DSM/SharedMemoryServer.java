import java.io.*;
import java.net.*;

public class SharedMemoryServer {
    private static int sharedVariable = 50;  // Initial value of shared variable

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("SharedMemoryServer started on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String command;
                while ((command = in.readLine()) != null) {
                    if (command.equalsIgnoreCase("get")) {
                        out.println("Accessed Shared Variable: " + sharedVariable);
                    } else if (command.startsWith("set")) {
                        try {
                            int newValue = Integer.parseInt(command.split(" ")[1]);
                            sharedVariable = newValue;
                            out.println("Updated Shared Variable: " + sharedVariable);
                        } catch (Exception e) {
                            out.println("Invalid set command. Use: set <integer_value>");
                        }
                    } else if (command.equalsIgnoreCase("exit")) {
                        out.println("Disconnecting...");
                        break;
                    } else {
                        out.println("Invalid Command");
                    }
                }
                clientSocket.close();
                System.out.println("Client disconnected...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
