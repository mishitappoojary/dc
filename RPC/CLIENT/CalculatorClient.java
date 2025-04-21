import java.io.*;
import java.net.*;

public class CalculatorClient {
    public static void main(String[] args) {
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        
        try {
            // Establish connection to the server on localhost and port 5000
            socket = new Socket("localhost", 5000);

            // Set up input and output streams
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            // Create a CalculatorRequest object
            CalculatorRequest request = new CalculatorRequest(10, 5, "subtract");

            // Send the request object to the server
            oos.writeObject(request);
            oos.flush();

            // Receive the result from the server
            int result = ois.readInt();
            System.out.println("Result from server: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the streams and socket
                if (ois != null) ois.close();
                if (oos != null) oos.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
