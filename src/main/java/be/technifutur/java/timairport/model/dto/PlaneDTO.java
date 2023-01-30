package be.technifutur.java.timairport.model.dto;

import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Builder
public class PlaneDTO {

    private long id;

    private String callSign;

    private LocalDate registrationDate;
    private boolean inMaintenance;

    private CompanyDTO company;
    private TypeDTO type;

    @Data
    @Builder
    public static class TypeDTO {
        private long typeId;
        private String typeName;
        private int capacity;
    }

    @Data
    @Builder
    public static class CompanyDTO {

        private long id;
        private String name;
        private String originCountry;
    }

    public static PlaneDTO from (Plane entity) {

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
}
