import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Configuration implements Serializable {

    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;

    //Creating Configuration constructor
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        if (totalTickets < 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maxTicketCapacity <= 0) {
            throw new IllegalArgumentException("have to be positive value");
        }
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    //Creating getters
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void displayConfiguration() {
        System.out.println("System Configuration:");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate + " ms");
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate + " ms");
        System.out.println("Maximum Ticket Capacity: " + maxTicketCapacity);
    }

    // Save configuration to a file
    public void saveConfiguration(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
            log("Configuration saved: " + filePath);
        } catch (IOException e) {
            log("Error in saving configuration: " + e.getMessage());
        }
    }

    // Load configuration from a file
    public static Configuration loadConfiguration(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Configuration config = (Configuration) ois.readObject();
            log("Configuration loaded: " + filePath);
            return config;
        } catch (IOException | ClassNotFoundException e) {
            log("Error in loading configuration: " + e.getMessage());
            return null;
        }
    }

    // Log message
    private static void log(String message) {
        System.out.println( message);
    }
}