package be.technifutur.java.timairport.model.entity;

import be.technifutur.java.timairport.service.PilotService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Long id;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", nullable = false)
    private Airport departure;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destination;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false)
    private Plane plane;

    @ManyToOne
    @JoinColumn(name = "captain_id", nullable = false)
    private Pilot captain;

    @ManyToOne
    @JoinColumn(name = "first_officer", nullable = false)
    private Pilot firstOfficer;

    @Column(nullable = false)
    private boolean cancelled;

    @OneToMany(mappedBy = "flight")
    private List<Booking> bookings;
}
