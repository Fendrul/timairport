package be.technifutur.java.timairport.respository;

import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Plane a SET a.inMaintenance = ?2 WHERE a.id = ?1")
    void updateMaintenance(long id, boolean b);

    @Modifying
    @Transactional
    @Query("UPDATE Plane a SET a.company = ?2 WHERE a.id = ?1")
    void updateCompany(long id, Company company);

}
