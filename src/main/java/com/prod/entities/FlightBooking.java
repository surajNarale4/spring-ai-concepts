package com.prod.entities;


import com.prod.entities.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlightBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    private Instant dipartureTime;
    
    private String time;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @CreatedDate
    private Instant bookedAt;

    
    
}
