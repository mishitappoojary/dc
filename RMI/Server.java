import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

interface Calculator extends Remote {
    double add(double a, double b) throws RemoteException;
    double subtract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
}

public class Server extends UnicastRemoteObject implements Calculator {
    protected Server() throws RemoteException {
        super();
    }
    public double add(double a, double b) throws RemoteException {
        return a + b;
    }
    public double subtract(double a, double b) throws RemoteException {
        return a - b;
    }
    public double multiply(double a, double b) throws RemoteException {
        return a * b;
    }
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); 
            Server server = new Server(); 
            Naming.rebind("CalculatorService", server);
            System.out.println("RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
