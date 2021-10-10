package com.example.routingSimulation.controller;

import com.example.routingSimulation.api.RoutingService;
import com.example.routingSimulation.dto.RoutePoints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class RoutingController {

    private final RoutingService routingService;

    @Autowired
    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/getPoints")
    public RoutePoints getPointsOnRoute(@RequestParam(name = "origin") String origin, @RequestParam(name = "destination") String destination)
    {
        log.info("Request received for origin; {} & destination: {}",origin,destination);
        return routingService.getPointOnRoute(origin, destination);
    }
}
