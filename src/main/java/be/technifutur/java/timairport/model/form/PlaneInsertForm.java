package be.technifutur.java.timairport.model.form;

import be.technifutur.java.timairport.constraints.BeforeDays;
import be.technifutur.java.timairport.constraints.Not0;
import be.technifutur.java.timairport.model.entity.Plane;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaneInsertForm {

    @NotNull
    @Pattern(regexp = "[A-Z]-[A-Z]{4}|[A-Z]{2}-[A-Z]{3}|N[0-9]{1,5}[A-Z]{0,2}")
    public String callSign;
    @NotNull
    @BeforeDays(5)
    public LocalDate registrationDate;
    @Not0
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
