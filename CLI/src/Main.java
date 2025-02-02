import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //File path
        String configFilePath = "config.ser";
        Configuration config = null;


        while (true) {
            try {
                //Display menu option
                System.out.println("Real-time Event Ticketing System");
                System.out.println("1. Display the latest configuration");
                System.out.println("2. Update the configuration and run the system");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //Diaplay saved configuration
                        config = Configuration.loadConfiguration(configFilePath);
                        if (config == null) {
                            System.out.println("No configuration found yet");
                        } else {
                            System.out.println("Latest Configuration:");
                            config.displayConfiguration();
                        }
                        break;

                    case 2:
                        //Update the configuration
                        config = newConfiguration(scanner, configFilePath);
                        System.out.println("\nRunning the system with the updated configuration...");
                        runSystem(config);
                        break;

                    case 3:
                        System.out.println("Exiting the program. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    //Create new configuration
    private static Configuration newConfiguration(Scanner scanner, String configFilePath) {
        Configuration config = null;
        while (config == null) {
            try {
                System.out.println("\nUpdating the configuration:");

                System.out.print("Enter Total Tickets: ");
                int totalTickets = scanner.nextInt();

                System.out.print("Enter Ticket Release Rate in ms: ");
                int ticketReleaseRate = scanner.nextInt();

                System.out.print("Enter Customer Retrieval Rate in ms): ");
                int customerRetrievalRate = scanner.nextInt();

                System.out.print("Enter Maximum Ticket Capacity: ");
                int maxTicketCapacity = scanner.nextInt();

                // Ensure total tickets are less than the maximum capacity
                if (totalTickets >= maxTicketCapacity) {
                    throw new IllegalArgumentException("Total tickets must be less than the maximum ticket capacity.");
                }

                // Create a new configuration object
                config = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                config.saveConfiguration(configFilePath); // Save the updated configuration
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please enter numeric values.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        return config;
    }

    //Run configuration
    private static void runSystem(Configuration config) {
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // Create vendor and customer threads
        Thread vendorThread = new Thread(new Vendor("V001", config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool));

        Thread customerThread = new Thread(new Customer("C001", config.getCustomerRetrievalRate(), ticketPool));


        vendorThread.start();
        customerThread.start();

        // Monitor threads
        while (vendorThread.isAlive() || customerThread.isAlive()) {
            if (ticketPool.isFull()) {
                System.out.println("System interuptted");
                vendorThread.interrupt(); // Stop the vendor thread
                customerThread.interrupt(); // Stop the customer thread
                break;
            }
        }
    }
}