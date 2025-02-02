package com.springdemo.objectproject.Application;

import java.util.concurrent.BlockingQueue;

public class Customer implements Runnable {
    private final TicketPool pool;
    private final int retrievalRate;
    private final BlockingQueue<String> logQueue;

    public Customer(TicketPool pool, int retrievalRate, BlockingQueue<String> logQueue) {
        this.pool = pool;
        this.retrievalRate = retrievalRate;
        this.logQueue = logQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ticket ticket = pool.getTicket();
                String log = "Customer bought: " + ticket;
                System.out.println(log);
                logQueue.offer(log);
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
