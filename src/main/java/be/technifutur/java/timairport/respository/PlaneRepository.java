package be.technifutur.java.timairport.respository;

import be.technifutur.java.timairport.model.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

}
