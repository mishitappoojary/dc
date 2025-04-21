import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("RPC Server is running...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected...");

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request;
                while ((request = in.readLine()) != null) {
                    String[] parts = request.split(" ");
                    String operation = parts[0];
                    double a = Double.parseDouble(parts[1]);
                    double b = Double.parseDouble(parts[2]);
                    double result = 0;

                    switch (operation) {
                        case "add":
                            result = a + b;
                            break;
                        case "subtract":
                            result = a - b;
                            break;
                        case "multiply":
                            result = a * b;
                            break;
                        case "divide":
                            if (b == 0) {
                                out.println("Error: Division by zero");
                                continue;
                            }
                            result = a / b;
                            break;
                        default:
                            out.println("Error: Invalid operation");
                            continue;
                    }
                    out.println(result);
                }
                System.out.println("Client disconnected...");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
