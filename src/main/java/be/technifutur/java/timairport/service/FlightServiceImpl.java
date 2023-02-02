package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.exception.ValidationException;
import be.technifutur.java.timairport.model.entity.*;
import be.technifutur.java.timairport.model.form.FlightInsertForm;
import be.technifutur.java.timairport.respository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepo;
    private final PilotRepository pilotRepo;
    private final PlaneRepository planeRepo;
    private final AirportRepository airportRepo;
    private final CompanyRepository companyRepo;
    @PersistenceContext
    private EntityManager em;

    public FlightServiceImpl(FlightRepository flightRepo, PilotRepository pilotRepo, PlaneRepository planeRepo, AirportRepository airportRepo, EntityManager em, CompanyRepository companyRepo) {
        this.flightRepo = flightRepo;
        this.pilotRepo = pilotRepo;
        this.planeRepo = planeRepo;
        this.airportRepo = airportRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public void create(FlightInsertForm form) {
        Flight flight = form.toEntity();

        if (!flight.getDepartureTime().isAfter(flight.getArrivalTime().minusDays(2)))
            throw new ValidationException("The arrival date have to be maximum 2 days after the departure date");

        if (flight.getDepartureTime().isAfter(flight.getArrivalTime()) || flight.getDepartureTime().isEqual(flight.getArrivalTime()))
            throw new ValidationException("The arrival date have to be after the departure date");

        Pilot captain = pilotRepo.findById(form.getCaptainId())
                .orElseThrow(RuntimeException::new);
        Pilot firstOfficer = pilotRepo.findById(form.getFirstOfficer())
                .orElseThrow(RuntimeException::new);
        if (captain == firstOfficer)
            throw new ValidationException("Captain and first officer must have a different ID");

        Airport airportDeparture = airportRepo.findById(form.getAirportDepartureId())
                .orElseThrow(RuntimeException::new);
        Airport airportDestination = airportRepo.findById(form.getAirportArrivalId())
                .orElseThrow(RuntimeException::new);

        if (airportDeparture == airportDestination)
            throw new ValidationException("Airport arrival must be different from the airport departure");

        List<Plane> planes;
        {
            String sqlQuery = """
                    SELECT p
                    FROM Plane p
                    WHERE p.type IN (
                        SELECT a.planeTypeAllowed
                        FROM Airport a
                        WHERE a.id = ?1 OR a.id = ?2
                    )
                    """;
            TypedQuery<Plane> querry = em.createQuery(sqlQuery, Plane.class);
            querry.setParameter(1, airportDeparture.getId());
            querry.setParameter(2, airportDestination.getId());

            Company company = companyRepo.findById(form.getCompanyID())
                    .orElseThrow(RuntimeException::new);

            planes = querry.getResultList().stream()
                    .filter(p -> p.getCompany().getId() == company.getId()
                            && airportDeparture.getPlaneTypeAllowed().contains(p.getType()) && airportDestination.getPlaneTypeAllowed().contains(p.getType())
                            && !p.isInMaintenance())
                    .toList();
        }

        if (planes.isEmpty())
            throw new ValidationException("No plane available for this flight");

        List<Plane> planesInAirport = new ArrayList<>();
        for (Plane plane : planes) {
            String sqlQuerry = """
                    SELECT f
                    FROM Flight f
                    WHERE f.plane.id = ?1
                    AND f.arrivalTime < CURRENT_TIMESTAMP
                    AND f.destination.id = ?2
                    ORDER BY f.arrivalTime DESC
                    """;
            TypedQuery<Flight> querry = em.createQuery(sqlQuerry, Flight.class);
            querry.setParameter(1, plane.getId());
            querry.setParameter(2, airportDestination.getId());

            List<Flight> lastFlight = querry.setMaxResults(1).getResultList();

            if (!lastFlight.isEmpty() && lastFlight.get(0).getDestination().equals(airportDeparture)) {
                planesInAirport.add(
                        planeRepo.findById(lastFlight.get(0).getPlane().getId())
                                .orElseThrow(RuntimeException::new)
                );
            }
        }
        if (!planesInAirport.isEmpty())
            planes = planesInAirport;

        Plane plane = planes.get((int) (Math.random() * planes.size()));

        flight.setCaptain(captain);
        flight.setFirstOfficer(firstOfficer);
        flight.setPlane(plane);
        flight.setDeparture(airportDeparture);
        flight.setDestination(airportDestination);

        flightRepo.save(flight);
    }
}
