package be.technifutur.java.timairport.mapper;

import be.technifutur.java.timairport.exception.RessourceNotFoundException;
import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import org.springframework.stereotype.Service;

@Service //Autre manière de faire un mapper
public class PlaneMapper {

    public PlaneDTO toDto(Plane entity) {

        if (entity == null)
            return null;

        return PlaneDTO.builder()
                .id(entity.getId())
                .inMaintenance((entity.isInMaintenance()))
                .callSign(entity.getCallSign())
                .registrationDate((entity.getRegistrationDate()))
                .company(
                        PlaneDTO.CompanyDTO.builder()
                                .id(entity.getCompany().getId())
                                .name(entity.getCompany().getName())
                                .originCountry(entity.getCompany().getOriginCountry())
                                .build()
                )
                .type(
                        PlaneDTO.TypeDTO.builder()
                                .typeId(entity.getType().getId())
                                .typeName(entity.getType().getName())
                                .capacity(entity.getType().getCapacity())
                                .build()
                )
                .build();
    }

    public Plane toEntity(PlaneInsertForm form) {
        Plane entity = new Plane();

        entity.setCallSign(form.callSign);
        entity.setRegistrationDate(form.registrationDate);

        return entity;
    }

}
