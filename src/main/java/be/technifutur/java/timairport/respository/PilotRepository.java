package be.technifutur.java.timairport.respository;

import be.technifutur.java.timairport.model.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PilotRepository extends JpaRepository<Pilot, Long> {

    public List<Pilot> findByFirstNameStartingWith(String start);

    @Query("SELECT p FROM Pilot p WHERe p.firstName = p.lastName")
    List<Pilot> findWithSameName();
}
