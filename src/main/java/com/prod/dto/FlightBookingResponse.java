package com.prod.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record FlightBookingResponse(String city, Long userId, Instant departureTime) {
}
