package be.technifutur.java.timairport.model.dto;

import be.technifutur.java.timairport.model.entity.Flight;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FlightDTO {

    private long id;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private boolean cancelled;
    private AirportDTO airportDeparture;
    private AirportDTO airportArrival;
    private PilotDTO captain;
    private PilotDTO firstoOfficer;
    private PlaneDTO plane;

    @Data
    @Builder
    public static class AirportDTO {
        private long id;
        private String name;
        private String country;
        private String city;
    }

    @Data
    @Builder
    public static class PilotDTO {
        private long id;
        private String firstName;
        private String lastName;
    }

    @Data
    @Builder
    public static class PlaneDTO {
        private long id;
        private String callSign;
    }

    public static FlightDTO from(Flight entity) {
        if (entity == null)
            return null;

        return FlightDTO.builder()
                .id(entity.getId())
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .airportDeparture(
                        AirportDTO.builder()
                                .id(entity.getDeparture().getId())
                                .name(entity.getDeparture().getName())
                                .country(entity.getDeparture().getCountry())
                                .city(entity.getDeparture().getCity())
                                .build()
                )
                .airportArrival(
                        AirportDTO.builder()
                                .id(entity.getDestination().getId())
                                .name(entity.getDestination().getName())
                                .country(entity.getDestination().getCountry())
                                .city(entity.getDestination().getCity())
                                .build()
                )
                .captain(
                        PilotDTO.builder()
                                .id(entity.getCaptain().getId())
                                .lastName(entity.getCaptain().getLastName())
                                .firstName(entity.getCaptain().getFirstName())
                                .build()
                )
                .firstoOfficer(
                        PilotDTO.builder()
                                .id(entity.getFirstOfficer().getId())
                                .lastName(entity.getFirstOfficer().getLastName())
                                .firstName(entity.getFirstOfficer().getFirstName())
                                .build()
                )
                .plane(PlaneDTO.builder()
                        .id(entity.getPlane().getId())
                        .callSign(entity.getPlane().getCallSign())
                        .build()
                )
                .build();
    }
}
