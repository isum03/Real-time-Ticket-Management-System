import java.util.ArrayList;
import java.util.List;

public class Vendor implements Runnable {
    private String vendorId;
    private int ticketsPerRelease;
    private int releaseInterval;
    private TicketPool ticketPool;

    //Creating vendor constructor
    public Vendor(String vendorId, int ticketsPerRelease, int releaseInterval, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseInterval = releaseInterval;
        this.ticketPool = ticketPool;
    }

    //Run the vendor thread
    @Override
    public void run() {
        //Intialize ticketId
        int ticketCount = 1;
        try {
            while (true) {
                //
                List<Ticket> ticketsAdd = new ArrayList<>();
                //Adding tickets
                for (int i = 0; i < ticketsPerRelease; i++) {
                    //Create a new tickets
                    ticketsAdd.add(new Ticket(ticketCount++, "Event101"));
                }
                //Add tickets to pool
                ticketPool.addTickets(ticketsAdd, vendorId);
                //Log actions when adding tickets
                Logger.log("Vendor " + vendorId + " added tickets: " + ticketsAdd + " - Current pool size: " + ticketPool.getCurrentSize());
                //Keep in interval between vendor adding tickets
                Thread.sleep(releaseInterval);
            }
        } catch (InterruptedException e) {
            //Log details in when system stop
            Logger.log("Vendor " + vendorId + " has stopped.");
            Thread.currentThread().interrupt();
        }
    }
}
