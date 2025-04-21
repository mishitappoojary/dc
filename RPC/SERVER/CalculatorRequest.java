import java.io.Serializable;

public class CalculatorRequest implements Serializable {
    private static final long serialVersionUID = 1L;  // It's good practice to add a serial version ID
    private int num1;
    private int num2;
    private String operation;

    // Constructor, getters, and setters
    public CalculatorRequest(int num1, int num2, String operation) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
