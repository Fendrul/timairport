package be.technifutur.java.timairport.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class TypePlane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_plane_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @ManyToMany(mappedBy = "planeTypeAllowed")
    private List<Airport> airports;

    @OneToMany(mappedBy = "type")
    private List<Plane> planes = new java.util.ArrayList<>();
}
