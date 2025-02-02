public class Ticket {
    private int ticketId;
    private String eventId;

    //Creating Ticket Constructor
    public Ticket(int ticketId, String eventId) {
        this.ticketId = ticketId;
        this.eventId = eventId;
    }
    //Creating getters and setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId: " + ticketId +
                " eventId: " + eventId + "}";
    }
}