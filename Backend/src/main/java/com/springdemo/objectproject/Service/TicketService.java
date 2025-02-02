package com.springdemo.objectproject.Service;

import com.springdemo.objectproject.Application.Customer;
import com.springdemo.objectproject.Application.TicketPool;
import com.springdemo.objectproject.Application.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TicketService {
    private TicketPool ticketPool;
    private ExecutorService executorService;
    private BlockingQueue<String> logQueue;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TicketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.logQueue = new LinkedBlockingQueue<>();
    }

    public void initialize(TicketPool pool) {
        this.ticketPool = pool;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void startSimulation(int ticketReleaseRate, int retrievalRate) {
        if (ticketPool == null) {
            throw new IllegalStateException("Ticket pool is not initialized.");
        }

        Vendor vendor = new Vendor(ticketPool, ticketReleaseRate, logQueue);
        Customer customer = new Customer(ticketPool, retrievalRate, logQueue);

        executorService.submit(() -> runWithLogging(vendor));
        executorService.submit(() -> runWithLogging(customer));
    }

    private void runWithLogging(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String log = logQueue.take();
                System.out.println(log); // CLI log
                messagingTemplate.convertAndSend("/topic/logs", log); // WebSocket log
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stopSimulation() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    public List<String> getLogs() {
        return List.copyOf(logQueue); // Return a copy of logs
    }
}