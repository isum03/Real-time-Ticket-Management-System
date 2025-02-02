package com.springdemo.objectproject.Controller;

import com.springdemo.objectproject.Application.Configuration;
import com.springdemo.objectproject.Application.TicketPool;
import com.springdemo.objectproject.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private LoggingController loggingController;

    private final TicketService service;

    @Autowired
    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/start")
    public String startSystem(@RequestBody Configuration config) throws IOException {
        loggingController.startLogging();
        service.initialize(new TicketPool(config.getMaxTicketCapacity()));
        service.startSimulation(config.getTicketReleaseRate(), config.getRetrievalRate());
        return "Ticket system simulation started!";
    }

    @PostMapping("/stop")
    public String stopSystem() {
        service.stopSimulation();
        return "Ticket system simulation stopped!";
    }
}
