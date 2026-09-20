package com.prod.services;


import com.prod.dto.FlightBookingResponse;
import com.prod.entities.FlightBooking;
import com.prod.entities.User;
import com.prod.entities.enums.BookingStatus;
import com.prod.exception.ResourceNotFoundException;
import com.prod.repositories.FlightBookingRepository;
import com.prod.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBookingService {

    private final FlightBookingRepository fligtBookingRepository;
    private final UserRepository userRepository;

    public FlightBookingResponse creatBooking(Long userId, String city, Instant departureTime ){

        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user with give id not found"));

        FlightBooking flightBooking = FlightBooking.builder()
                .user(user)
                .bookingStatus(BookingStatus.CONFIRM)
                .dipartureTime(departureTime)
                .build();
        fligtBookingRepository.save(flightBooking);

        return FlightBookingResponse.builder()
                .city(city)
                .departureTime(departureTime)
                .userId(userId)
                .build();

    }

    public List<FlightBookingResponse> getAllBooking(Long userId) {
        return fligtBookingRepository.findByUserId(userId);
    }
}
