
package com.springdemo.objectproject.Application;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int retrievalRate;
    private int maxTicketCapacity;

    // Getters and Setters
    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }
    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int rate) { this.ticketReleaseRate = rate; }
    public int getRetrievalRate() { return retrievalRate; }
    public void setRetrievalRate(int rate) { this.retrievalRate = rate; }
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int capacity) { this.maxTicketCapacity = capacity; }
}
