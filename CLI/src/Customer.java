import java.util.logging.*;

public class Customer implements Runnable {
    private final String customerId;
    private final int retrievalInterval;
    private final TicketPool ticketPool;

    //Creating customer constructor
    public Customer(String customerId, int retrievalInterval, TicketPool ticketPool) {
        this.customerId = customerId;
        this.retrievalInterval = retrievalInterval;
        this.ticketPool = ticketPool;
    }

    //Run the customer thread
    @Override
    public void run() {
        try {
            while (true) {
                //Book tickets from the ticketpool
                Ticket ticket = ticketPool.removeTicket(customerId);
                //If ticketing booking is successful
                if (ticket != null) {
                    Logger.log("Customer " + customerId + " book ticket: " + ticket + " - Current pool size: " + ticketPool.getCurrentSize());
                }
                //Keep in the customer booking tickets
                Thread.sleep(retrievalInterval);
            }
        } catch (InterruptedException e) {
            Logger.log("Customer " + customerId + " has stop booking tickets.");
            Thread.currentThread().interrupt();
        }
    }
}
