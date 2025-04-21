import java.util.Random;
import java.util.Scanner;

class Process {
    int id;
    boolean isActive;

    Process(int id) {
        this.id = id;
        this.isActive = true;
    }
}

public class BullyElection {
    static Process[] processes;
    static int coordinatorId;

    public static void startElection(int initiatorId) {
        System.out.println("\nProcess " + initiatorId + " starts election...");
        int highestId = initiatorId;

        for (Process p : processes) {
            if (p.id > initiatorId && p.isActive) {
                System.out.println("Process " + initiatorId + " sends election message to Process " + p.id);
                highestId = Math.max(highestId, p.id);
            }
        }

        coordinatorId = highestId;
        announceCoordinator();
    }

    public static void announceCoordinator() {
        System.out.println("\nProcess " + coordinatorId + " is elected as the new Coordinator!");
    }

    public static void simulateFailure(int id) {
        for (Process p : processes) {
            if (p.id == id) {
                p.isActive = false;
                System.out.println("\nProcess " + id + " has failed.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        processes = new Process[n];

        // Assign random process IDs
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            processes[i] = new Process(random.nextInt(100) + 1);
        }

        // Sort processes by ID for clarity
        java.util.Arrays.sort(processes, (a, b) -> a.id - b.id);

        System.out.println("Process IDs:");
        for (Process p : processes) {
            System.out.print(p.id + " ");
        }
        System.out.println();

        // Elect leader initially
        coordinatorId = processes[n - 1].id;
        announceCoordinator();

        while (true) {
            System.out.println("\n1. Simulate Failure  2. Start Election  3. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter process ID to fail: ");
                int failId = scanner.nextInt();
                simulateFailure(failId);
            } else if (choice == 2) {
                System.out.print("Enter process ID to start election: ");
                int startId = scanner.nextInt();
                startElection(startId);
            } else {
                break;
            }
        }

        scanner.close();
    }
}
