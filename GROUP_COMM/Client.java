import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your username: ");
            String username = consoleReader.readLine();

            Thread readThread = new Thread(new ReadHandler(socket));
            Thread writeThread = new Thread(new WriteHandler(socket, username));

            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.join();
        } catch (IOException | InterruptedException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static class ReadHandler implements Runnable {
        private Socket socket;

        public ReadHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            }
        }
    }

    private static class WriteHandler implements Runnable {
        private Socket socket;
        private String username;

        public WriteHandler(Socket socket, String username) {
            this.socket = socket;
            this.username = username;
        }

        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

                out.println(username); // Send username to the server

                String message;
                while (true) {
                    message = consoleReader.readLine();
                    if (message.equalsIgnoreCase("exit")) {
                        out.println(username + " has left the chat.");
                        break;
                    }
                    out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server: " + e.getMessage());
            }
        }
    }
}
