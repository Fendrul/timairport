package be.technifutur.java.timairport.model.form;

import be.technifutur.java.timairport.exception.RessourceNotFoundException;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaneInsertForm {

    @NotNull
    @Pattern(regexp = "[A-Z]-[A-Z]{4}|[A-Z]{2}-[A-Z]{3}|N[0-9]{1,5}[A-Z]{0,2}")
    public String callSign;
    @NotNull
    @PastOrPresent
    public LocalDate registrationDate;
    @NotNull
    public Long companyId;
    @NotNull
    public Long typeId;

    public Plane toEntity() {

        Plane plane = new Plane();

        plane.setCallSign(this.callSign);
        plane.setRegistrationDate(this.registrationDate);

        return  plane;

    }

}
