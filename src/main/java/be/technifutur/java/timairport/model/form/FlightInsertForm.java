package be.technifutur.java.timairport.model.form;

import be.technifutur.java.timairport.model.entity.Flight;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightInsertForm {

    @NotNull
    @Future
    public LocalDateTime departureTime;

    @NotNull
    @Future
    public LocalDateTime arrivalTime;

    @NotNull
    public long airportDepartureId;

    @NotNull
    public long airportArrivalId;

    @NotNull
    public long captainId;

    @NotNull
    public long firstOfficer;

    @NotNull
    public long companyID;

    public Flight toEntity() {

        Flight flight = new Flight();

        flight.setDepartureTime(this.departureTime);
        flight.setArrivalTime(this.arrivalTime);

        return flight;
    }

}
