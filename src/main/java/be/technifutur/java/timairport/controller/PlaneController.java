package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.dto.PlaneDTO;
import be.technifutur.java.timairport.model.entity.Plane;
import be.technifutur.java.timairport.model.form.PlaneInsertForm;
import be.technifutur.java.timairport.respository.PlaneRepository;
import be.technifutur.java.timairport.service.PlaneService;
import be.technifutur.java.timairport.service.PlaneServiceImpl;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plane")
public class PlaneController {

    private final PlaneService planeService;
    private final PlaneRepository planeRepository;

    public PlaneController(PlaneService planeService,
                           PlaneRepository planeRepository) {
        this.planeService = planeService;
        this.planeRepository = planeRepository;
    }

    @GetMapping("/{id:[0:9]+}")
    public PlaneDTO getOne(@PathVariable long id) {
        return planeService.getOne(id);
    }

    @GetMapping("/all")
    public List<PlaneDTO> getAll() {
        return planeService.getAll();
    }

    @PostMapping("/add")
    public void create(@RequestBody @Valid PlaneInsertForm form) {
        planeService.create(form);
    }

    @PatchMapping("update/maintenance/{id:[0-9]+}")
    public void updateMaintenance(@PathVariable long id, @RequestParam boolean maintenance) {
        planeService.updateMaintenance(id, maintenance);
        System.out.println("Cé okéééé");
    }

    @PatchMapping("update/company/{id:[0-9]+}")
    public void updateCompany(@PathVariable long planeID, @RequestParam long companyID) {
        planeService.updateCompanyName(planeID, companyID);
    }

    @PatchMapping("/update/{id:[0-9]+}")
    public void update(@PathVariable long id, Map<String, String> params) {
        Map<String, Object> mapValues = new HashMap<>();

        if (params.containsKey("companyId"))
            mapValues.put("CompanyId", Long.parseLong(params.get("companyId")));

        if (params.containsKey("inMaintenance"))
            mapValues.put("inMaintenance", Boolean.parseBoolean(params.get("maintenance")));

        planeService.update(id, mapValues);
    }

}
