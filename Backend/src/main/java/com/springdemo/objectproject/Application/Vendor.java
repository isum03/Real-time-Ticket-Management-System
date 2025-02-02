package com.springdemo.objectproject.Application;

import java.util.concurrent.BlockingQueue;

public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseRate;
    private final BlockingQueue<String> logQueue;

    public Vendor(TicketPool pool, int releaseRate, BlockingQueue<String> logQueue) {
        this.pool = pool;
        this.releaseRate = releaseRate;
        this.logQueue = logQueue;
    }

    @Override
    public void run() {
        int ticketId = 1;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (pool.addTicket(new Ticket(ticketId++, "Event-" + ticketId))) {
                    String log = "Vendor released ticket: " + ticketId;
                    System.out.println(log);
                    logQueue.offer(log);
                }
                Thread.sleep(releaseRate);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
