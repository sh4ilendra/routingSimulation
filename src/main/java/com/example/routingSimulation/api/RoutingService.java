package com.example.routingSimulation.api;

import com.example.routingSimulation.dto.RouteDirection;
import com.example.routingSimulation.dto.RoutePoints;
import com.example.routingSimulation.dto.StartLocation;
import com.example.routingSimulation.dto.Step;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoutingService {

    @Value("${api.key}")
    private String apiKey;

    public static OkHttpClient client = new OkHttpClient();
    public static ObjectMapper objectMapper = new ObjectMapper();

    private String generateURL(String origin, String destination)
    {
        StringBuilder directionsAPIURL = new StringBuilder();
        directionsAPIURL.append("https://maps.googleapis.com/maps/api/directions/json?origin=")
                .append(origin)
                .append("&destination=")
                .append(destination)
                .append("&key=")
                .append(apiKey);
        log.info("Google API request URL is {}",directionsAPIURL.toString());
        return directionsAPIURL.toString();
    }

    private RouteDirection getGAPIResponse(String origin, String destination)
    {
        RouteDirection routeDirection = null;
        Request request = new Request.Builder()
                .url(generateURL(origin,destination))
                .method("GET", null)
                .build();
        try
        {
            Response response = client.newCall(request).execute();
            routeDirection  = objectMapper.readValue(response.body().string(), RouteDirection.class);

        }
        catch (Exception e)
        {
            log.error("Exception occurred while API call: {}",e.getMessage());
        }
        return  routeDirection;
    }

    public RoutePoints getPointOnRoute(String origin, String destination)
    {
        log.info("origin is {} & destination is {}",origin,destination);
        RoutePoints routePoints = new RoutePoints();
        routePoints.setDistance("LatLngs at a constant distance interval of 50 m between A & B on the road");
        List<String> resultLocations = new ArrayList<>();
        resultLocations.add(origin);

        RouteDirection routeDirection = getGAPIResponse(origin,destination);

        if(routeDirection != null && routeDirection.getRoutes() != null
                && routeDirection.getRoutes().size() > 0
                && routeDirection.getRoutes().get(0).getLegs() != null
                && routeDirection.getRoutes().get(0).getLegs().size() > 0
                && routeDirection.getRoutes().get(0).getLegs().get(0).getSteps() != null)
        {
            List<Step> steps = routeDirection.getRoutes().get(0).getLegs().get(0).getSteps();
            for (Step step : steps) {
                StartLocation startLocation = step.getStartLocation();
                DecimalFormat df = new DecimalFormat("#.00000");
                resultLocations.add(df.format(startLocation.getLat()) + "," + df.format(startLocation.getLng()));
            }
        }
        resultLocations.add(destination);
        routePoints.setLocations(resultLocations);
        log.info("Response is {}",routePoints);

        return routePoints;
    }
}
