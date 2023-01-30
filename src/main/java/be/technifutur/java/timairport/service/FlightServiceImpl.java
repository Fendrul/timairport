package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.exception.ValidationException;
import be.technifutur.java.timairport.model.entity.Airport;
import be.technifutur.java.timairport.model.entity.Flight;
import be.technifutur.java.timairport.model.entity.Pilot;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.form.FlightInsertForm;
import be.technifutur.java.timairport.respository.AirportRepository;
import be.technifutur.java.timairport.respository.FlightRepository;
import be.technifutur.java.timairport.respository.PilotRepository;
import be.technifutur.java.timairport.respository.PlaneRepository;

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepo;
    private final PilotRepository pilotRepo;
    private final PlaneRepository planeRepo;
    private final AirportRepository airportRepo;

    public FlightServiceImpl(FlightRepository flightRepo, PilotRepository pilotRepo, PlaneRepository planeRepo, AirportRepository airportRepo) {
        this.flightRepo = flightRepo;
        this.pilotRepo = pilotRepo;
        this.planeRepo = planeRepo;
        this.airportRepo = airportRepo;
    }

    @Override
    public void create(FlightInsertForm form) {
        Flight flight = form.toEntity();

        Pilot captain = pilotRepo.findById(form.getCaptainId())
                .orElseThrow(RuntimeException::new);
        Pilot firstOfficer = pilotRepo.findById(form.getFirstOfficer())
                .orElseThrow(RuntimeException::new);
        if (captain == firstOfficer)
            throw new ValidationException("Captain and first officer must have a different ID");

        Plane plane = planeRepo.findById(form.getPlaneId())
                .orElseThrow(RuntimeException::new);
        if (plane.isInMaintenance())
            throw new ValidationException("A plane in maintenance can't be choosen for a flight");

        Airport airportDeparture = airportRepo.findById(form.getAirportDepartureId())
                .orElseThrow(RuntimeException::new);
        Airport airportDestination = airportRepo.findById(form.getAirportArrivalId())
                .orElseThrow(RuntimeException::new);

        if (airportDeparture == airportDestination)
            throw new ValidationException("Airport arrival must be different from the airport departure");
        if (!airportDeparture.getPlaneTypeAllowed().contains(plane.getType()))
            throw new ValidationException("The airport where the plane has to leave can't accept this type of plane");
        if (!airportDestination.getPlaneTypeAllowed().contains(plane.getType()))
            throw new ValidationException("The airport where the plane has to arrive can't accept this type of plane");

        flight.setCaptain(captain);
        flight.setFirstOfficer(firstOfficer);
        flight.setPlane(plane);
        flight.setDeparture(airportDeparture);
        flight.setDestination(airportDestination);

        flightRepo.save(flight);
    }
}
