
package com.springdemo.objectproject.Controller;


import com.springdemo.objectproject.Application.TicketPool;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketPoolController {

    private TicketPool ticketPool;

    @PostMapping("/initialize-pool")
    public String initializePool(@RequestParam int maxCapacity) {
        this.ticketPool = new TicketPool(maxCapacity);
        return "Ticket pool initialized with max capacity: " + maxCapacity;
    }

    @GetMapping("/pool-status")
    public String getPoolStatus() {
        if (ticketPool != null) {
            return "Ticket Pool initialized with max capacity: " + ticketPool.getMaxCapacity();
        }
        return "Ticket Pool is not yet initialized.";
    }
}
