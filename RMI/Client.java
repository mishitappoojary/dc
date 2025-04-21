import java.rmi.*;

public class Client {
    public static void main(String[] args) {
        try {
            Calculator calc = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

            double a = 22, b = 14;
            System.out.println("Addition: " + calc.add(a, b));
            System.out.println("Subtraction: " + calc.subtract(a, b));
            System.out.println("Multiplication: " + calc.multiply(a, b));
            System.out.println("Division: " + calc.divide(a, b));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
