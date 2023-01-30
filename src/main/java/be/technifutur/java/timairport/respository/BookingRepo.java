package be.technifutur.java.timairport.respository;

import be.technifutur.java.timairport.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
