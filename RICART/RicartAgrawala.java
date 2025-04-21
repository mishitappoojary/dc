import java.util.*;

public class RicartAgrawala {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Integer> process = new HashMap<>();

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        // Input timestamps
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter timestamp of process " + i + ": ");
            int ts = sc.nextInt();
            process.put(i, ts);
        }

        // Input processes requiring shared resource
        System.out.print("Enter processes requiring shared resource (space separated): ");
        sc.nextLine();  // Consume leftover newline
        String[] input = sc.nextLine().split(" ");
        List<Integer> p = new ArrayList<>();
        for (String s : input) {
            p.add(Integer.parseInt(s));
        }

        while (!p.isEmpty()) {
            int selectedTime = Integer.MAX_VALUE;
            int selectedProcess = -1;

            // Find process with the lowest timestamp
            for (int pi : p) {
                if (process.get(pi) < selectedTime) {
                    selectedTime = process.get(pi);
                    selectedProcess = pi;
                }
            }

            // Send timestamp to all other processes
            for (int i = 1; i <= n; i++) {
                if (i == selectedProcess) continue;
                System.out.println("Process " + selectedProcess + " sends timestamp(" + process.get(selectedProcess) + ") to Process " + i);
            }

            System.out.println("\nProcess " + selectedProcess + " has lowest timestamp = " + selectedTime);

            // Send OK message to selected process
            for (int i = 1; i <= n; i++) {
                if (i == selectedProcess) continue;
                System.out.println("Process " + i + " sends OK to Process " + selectedProcess);
            }

            System.out.println("Process " + selectedProcess + " is accessing shared resource\n");

            Thread.sleep(5000);  // Simulate time spent in critical section
            p.remove(Integer.valueOf(selectedProcess));
        }

        sc.close();
    }
}
