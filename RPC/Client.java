import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            double a = 10, b = 10;

            // Perform RPC calls
            sendRequest(out, in, "add", a, b);
            sendRequest(out, in, "subtract", a, b);
            sendRequest(out, in, "multiply", a, b);
            sendRequest(out, in, "divide", a, b);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendRequest(PrintWriter out, BufferedReader in, String operation, double a, double b) throws IOException {
        out.println(operation + " " + a + " " + b);
        System.out.println(operation + ": " + in.readLine());
    }
}
