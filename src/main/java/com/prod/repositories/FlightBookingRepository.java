package com.prod.repositories;

import com.prod.dto.FlightBookingResponse;
import com.prod.entities.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking,Long> {
    List<FlightBookingResponse> findByUserId(Long userId);
}
