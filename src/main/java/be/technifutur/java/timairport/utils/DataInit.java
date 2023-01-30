package be.technifutur.java.timairport.utils;

import be.technifutur.java.timairport.model.entity.Airport;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.respository.AirportRepository;
import be.technifutur.java.timairport.respository.CompanyRepository;
import be.technifutur.java.timairport.respository.PlaneRepository;
import be.technifutur.java.timairport.respository.TypePlaneRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInit implements InitializingBean {


    private final CompanyRepository companyRepository;
    private final TypePlaneRepository typePlaneRepo;
    private final PlaneRepository planeRepository;
    private final AirportRepository airportRepo;

    public DataInit(CompanyRepository companyRepository, TypePlaneRepository typePlaneRepository,
                    PlaneRepository planeRepository, AirportRepository airportRepository) {
        this.companyRepository = companyRepository;
        this.typePlaneRepo = typePlaneRepository;
        this.planeRepository = planeRepository;
        this.airportRepo = airportRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        TypePlane type = new TypePlane();
        type.setName("big plane");
        type.setCapacity(300);

        typePlaneRepo.save(type);

        type = new TypePlane();
        type.setName("average_plane");
        type.setCapacity(200);

        typePlaneRepo.save(type);

        type = new TypePlane();
        type.setName("small_plane");
        type.setCapacity(100);

        typePlaneRepo.save(type);

        Company company = new Company();
        company.setName("big money company");
        company.setOriginCountry("USO");

        companyRepository.save(company);

        company = new Company();
        company.setName("El Deedla mer de");
        company.setOriginCountry("Belgium");

        companyRepository.save(company);

        Plane plane = new Plane();
        plane.setCallSign("F-GCCC");
        plane.setRegistrationDate(LocalDate.of(2023, 01, 25));
        plane.setType(typePlaneRepo.findById(1L).orElseThrow(RuntimeException::new));
        plane.setCompany(companyRepository.findById(1L).orElseThrow(RuntimeException::new));

        planeRepository.save(plane);

        plane = new Plane();
        plane.setCallSign("F-GXNP");
        plane.setRegistrationDate(LocalDate.of(2023, 01, 25));
        plane.setType(typePlaneRepo.findById(1L).orElseThrow(RuntimeException::new));
        plane.setCompany(companyRepository.findById(1L).orElseThrow(RuntimeException::new));

        planeRepository.save(plane);

        Airport airport = new Airport();

        airport.setName("Huge plane commercialiser");
        airport.setCity("Hong-kong");
        airport.setPlaneParking(100);
        airport.setPlaneTypeAllowed(
                List.of(
                        typePlaneRepo.findById(1L).orElseThrow(RuntimeException::new)
                )
        );

        airportRepo.save(airport);

        airport = new Airport();

        airport.setName("Plane comedy");
        airport.setCity("Charleroi");
        airport.setPlaneParking(2);
        airport.setPlaneTypeAllowed(
                List.of(
                        typePlaneRepo.findById(2L).orElseThrow(RuntimeException::new)
                )
        );

        airportRepo.save(airport);

    }
}
