import java.io.*;
import java.net.*;

public class CalculatorServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            // Create a server socket listening on port 5000
            serverSocket = new ServerSocket(5000);
            System.out.println("Server is waiting for client...");

            // Accept a connection from the client
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");

            // Set up input and output streams
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // Read the CalculatorRequest object sent by the client
            CalculatorRequest request = (CalculatorRequest) ois.readObject();

            int result = 0;
            // Perform the requested operation
            switch (request.getOperation()) {
                case "add":
                    result = request.getNum1() + request.getNum2();
                    break;
                case "subtract":
                    result = request.getNum1() - request.getNum2();
                    break;
                case "multiply":
                    result = request.getNum1() * request.getNum2();
                    break;
                case "divide":
                    result = request.getNum1() / request.getNum2();
                    break;
                default:
                    System.err.println("Invalid operation");
            }

            // Send the result back to the client
            oos.writeInt(result);
            oos.flush();

        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                // Close resources
                if (ois != null) ois.close();
                if (oos != null) oos.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
