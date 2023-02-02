package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.exception.RessourceNotFoundException;
import be.technifutur.java.timairport.mapper.PlaneMapper;
import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import be.technifutur.java.timairport.model.entity.Company;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.entity.TypePlane;
import be.technifutur.java.timairport.respository.CompanyRepository;
import be.technifutur.java.timairport.respository.PlaneRepository;
import be.technifutur.java.timairport.respository.TypePlaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final PlaneRepository planeRepo;
    private final CompanyRepository companyRepo;
    private final TypePlaneRepository typePlaneRepo;

    public PlaneServiceImpl(PlaneRepository planeRepo, CompanyRepository companyRepo, TypePlaneRepository typePlaneRepo) {
        this.planeRepo = planeRepo;
        this.companyRepo = companyRepo;
        this.typePlaneRepo = typePlaneRepo;
    }

    @Override
    public void create(PlaneInsertForm form) {
        Plane plane = form.toEntity();

        Company company = companyRepo.findById(form.getCompanyId())
                .orElseThrow(RessourceNotFoundException::new);
        TypePlane typePlane = typePlaneRepo.findById(form.getTypeId())
                        .orElseThrow(RessourceNotFoundException::new);

        plane.setCompany(company);
        plane.setType(typePlane);

        planeRepo.save(plane);
    }

    @Override
    public PlaneDTO getOne(long id) {
        return planeRepo.findById(id)
                .map(PlaneDTO::from)
                .orElseThrow();
    }

    @Override
    public List<PlaneDTO> getAll() {
        return planeRepo.findAll().stream().map(PlaneDTO::from).toList();
    }

    @Override
    public void updateMaintenance(long id, boolean inMaintenance) {
        Plane plane = planeRepo.findById(id).orElseThrow(RuntimeException::new);
        plane.setInMaintenance(inMaintenance);
        planeRepo.save(plane);
    }

    @Override
    public void updateCompanyName(long planeID, long companyID) {
        Plane plane = planeRepo.findById(planeID).orElseThrow(RuntimeException::new);
        Company company = companyRepo.findById(companyID).orElseThrow(RuntimeException::new);

        plane.setCompany(company);
        planeRepo.save(plane);
    }

    @Override
    public void update(long idPlane, Map<String, Object> updateData) {
        Plane plane = planeRepo.findById(idPlane)
                .orElseThrow(RuntimeException::new);

        if (updateData.containsKey("CompanyId")) {
            long companyId = (long)updateData.get("companyId");
            Company company = companyRepo.findById(companyId)
                    .orElseThrow(RuntimeException::new);
        }

        if (updateData.containsKey("intMaintenance")) {
            long intMaintenance = (long)updateData.get("intMaintenance");
            Company company = companyRepo.findById(intMaintenance)
                    .orElseThrow(RuntimeException::new);
        }

        planeRepo.save(plane);

    }

    @Override
    public void delete(long id) {
        planeRepo.deleteById(id);
    }


}
