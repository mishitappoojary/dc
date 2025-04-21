import java.util.*;

class Server {
    int id;
    List<String> processes;

    public Server(int id) {
        this.id = id;
        this.processes = new ArrayList<>();
    }

    public void addProcess(String process) {
        processes.add(process);
    }

    public void removeProcess(String process) {
        processes.remove(process);
    }

    public boolean isEmpty() {
        return processes.isEmpty();
    }

    @Override
    public String toString() {
        return "Server " + id + " -> Processes: " + processes;
    }
}

class LoadBalancer {
    List<Server> servers;
    int nextServerIndex = 0;

    public LoadBalancer() {
        this.servers = new ArrayList<>();
    }

    public void addServer() {
        servers.add(new Server(servers.size() + 1));
        System.out.println("New server added: Server " + servers.size());
    }

    public void removeServer(int serverId) {
        servers.removeIf(server -> server.id == serverId);
        System.out.println("Server " + serverId + " removed.");
    }

    public void addProcess(String process) {
        if (servers.isEmpty()) {
            System.out.println("No servers available. Add a server first!");
            return;
        }
        servers.get(nextServerIndex).addProcess(process);
        System.out.println("Process " + process + " added to Server " + servers.get(nextServerIndex).id);
        nextServerIndex = (nextServerIndex + 1) % servers.size(); // Round-robin allocation
    }

    public void removeProcess(String process) {
        for (Server server : servers) {
            if (server.processes.contains(process)) {
                server.removeProcess(process);
                System.out.println("Process " + process + " removed from Server " + server.id);
                return;
            }
        }
        System.out.println("Process " + process + " not found.");
    }

    public void displayServers() {
        if (servers.isEmpty()) {
            System.out.println("No servers available.");
        } else {
            for (Server server : servers) {
                System.out.println(server);
            }
        }
    }
}

public class LoadBalancerSimulation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoadBalancer lb = new LoadBalancer();
        int choice;

        do {
            System.out.println("\n1. Add Server");
            System.out.println("2. Remove Server");
            System.out.println("3. Add Process");
            System.out.println("4. Remove Process");
            System.out.println("5. Display Servers");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    lb.addServer();
                    break;
                case 2:
                    System.out.print("Enter Server ID to remove: ");
                    int serverId = sc.nextInt();
                    lb.removeServer(serverId);
                    break;
                case 3:
                    System.out.print("Enter Process Name: ");
                    String process = sc.nextLine();
                    lb.addProcess(process);
                    break;
                case 4:
                    System.out.print("Enter Process Name to remove: ");
                    String removeProcess = sc.nextLine();
                    lb.removeProcess(removeProcess);
                    break;
                case 5:
                    lb.displayServers();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);

        sc.close();
    }
}
