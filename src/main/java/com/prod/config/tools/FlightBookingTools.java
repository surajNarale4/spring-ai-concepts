package com.prod.config.tools;


import com.prod.dto.FlightBookingResponse;
import com.prod.services.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {

    private final FlightBookingService flightBookingService;

    @Tool
    public FlightBookingResponse bookTheFlight(
        @ToolParam(description = "this is unique  user id e.g (1 or 200) ")    Long userId,
        @ToolParam(description = "this is destination where user want to book the flight")    String destination ,
        @ToolParam(description = "this departure time of flight which require format of ISO-8601 e.g (2025-12-25T14:30:00Z) ")    Instant departureTime

    ){
        return flightBookingService.creatBooking(userId,destination,departureTime);
    }

    @Tool
    public List<FlightBookingResponse> getAllFligutBooking(
          @ToolParam(description = "using this userid get all bookings ") Long userId
    ){
        return flightBookingService.getAllBooking(userId);
    }
}
