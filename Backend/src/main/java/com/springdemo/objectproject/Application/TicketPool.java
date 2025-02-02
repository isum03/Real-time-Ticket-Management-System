
package com.springdemo.objectproject.Application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketPool {
    private final int maxCapacity;
    private final List<Ticket> ticketList;

    // Constructor with the max capacity
    public TicketPool(@Value("${ticketpool.max-capacity:100}")int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.ticketList = new ArrayList<>();
    }

    public synchronized boolean addTicket(Ticket ticket) {
        if (ticketList.size() < maxCapacity) {
            ticketList.add(ticket);
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized Ticket getTicket() throws InterruptedException {
        while (ticketList.isEmpty()) {
            wait();
        }
        return ticketList.remove(0);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
