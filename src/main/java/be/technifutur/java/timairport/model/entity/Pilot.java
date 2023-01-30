package be.technifutur.java.timairport.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
public class Pilot extends  Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pilot_id", nullable = false)
    private Long id;

    @Column(name = "license_id", nullable = false, unique = true)
    private String licenseId;

    @Column(name = "license_acquisition", nullable = false)
    private LocalDate licenseAcquisition;

    @OneToMany(mappedBy = "captain")
    private List<Flight> flightsAsCaptain = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "firstOfficer")
    private List<Flight> flightsAsOfficer = new java.util.ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
