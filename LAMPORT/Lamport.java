import java.util.Scanner;

// Class to implement the Lamport Clock
class LamportClock {
    private int logicalClock;

    public LamportClock() {
        this.logicalClock = 0;
    }

    // Increment the logical clock for an internal event
    public void incrementClock() {
        logicalClock++;
    }

    // Get the current logical clock value
    public int getClock() {
        return logicalClock;
    }

    // Update clock based on received timestamp
    public void updateClock(int receivedTimestamp) {
        logicalClock = Math.max(logicalClock, receivedTimestamp) + 1;
    }

    // Send a message with the current logical clock value
    public void sendMessage(LamportClock receiverClock) {
        System.out.println("Sending message with timestamp: " + logicalClock);
        receiverClock.receiveMessage(logicalClock);
    }

    // Receive a message and update the clock accordingly
    public void receiveMessage(int senderTimestamp) {
        updateClock(senderTimestamp);
        System.out.println("Received message with timestamp: " + senderTimestamp + ". Updated clock: " + logicalClock);
    }
}

// Main class to simulate the Lamport Clock Algorithm
public class Lamport {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LamportClock process1 = new LamportClock();
        LamportClock process2 = new LamportClock();
        LamportClock process3 = new LamportClock();

        System.out.println("\n=== Lamport Clock Algorithm Simulation ===");

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Process 1 sends a message to Process 2");
            System.out.println("2. Process 2 sends a message to Process 3");
            System.out.println("3. Process 3 receives a message from Process 2");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n[Process 1]: Sending message to Process 2");
                    process1.incrementClock();
                    process1.sendMessage(process2);
                    break;

                case 2:
                    System.out.println("\n[Process 2]: Sending message to Process 3");
                    process2.incrementClock();
                    process2.sendMessage(process3);
                    break;

                case 3:
                    System.out.println("\n[Process 3]: Receiving message from Process 2");
                    process3.receiveMessage(process2.getClock());
                    break;

                case 4:
                    System.out.println("Exiting the simulation.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
