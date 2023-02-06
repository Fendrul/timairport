package be.technifutur.java.timairport.respository;

import be.technifutur.java.timairport.model.entity.Flight;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET cancelled = true WHERE f.id = ?1")
    void setCancelled(long id);
}
