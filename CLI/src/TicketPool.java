import java.util.List;
import java.util.ArrayList;

//
public class TicketPool {
    private int maximumTicketCapacity;
    private List<Ticket> ticketList;

    //Create TicketPool constructor
    public TicketPool(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketList = new ArrayList<>();
    }
    //Check the ticket class is full or not
    public synchronized boolean isFull() {
        return ticketList.size() >= maximumTicketCapacity;
    }

    // Vendor add ticket to the system
    public synchronized void addTickets(List<Ticket> ticketsAdd, String vendorId) {
        while (ticketList.size() + ticketsAdd.size() > maximumTicketCapacity) {
            try {
                Logger.log("Vendor " + vendorId + " waiting");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.log("Vendor " + vendorId + " interrupted.");
                return;
            }
        }

        //Adding tickets to the ticketpool
        ticketList.addAll(ticketsAdd);
        Logger.log("Vendor " + vendorId + " added " + ticketsAdd.size() + " tickets. Current pool size: " + ticketList.size());
        notifyAll();
    }

    //Customer book tickets from the system
    public synchronized Ticket removeTicket(String customerId) {
        while (ticketList.isEmpty()) {
            try {
                Logger.log("Customer " + customerId + " waiting");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Logger.log("Customer " + customerId + " was interrupted.");
                return null;
            }
        }

        //Removing ticket from the ticketpool
        Ticket ticket = ticketList.remove(0);
        Logger.log("Customer " + customerId + " booked ticket: " + ticket + ". Current pool size: " + ticketList.size());
        notifyAll();
        return ticket;
    }
    //Get the current ticket count
    public synchronized int getCurrentSize() {
        return ticketList.size();
    }


}