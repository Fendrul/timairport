package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.form.FlightInsertForm;
import be.technifutur.java.timairport.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightServ;

    public FlightController(FlightService flightServ) {
        this.flightServ = flightServ;
    }

    @GetMapping("/create")
    public void create(Model model) {
        model.addAttribute("flightForm", new FlightInsertForm());
    }

    @PostMapping("/create")
    public void create(@RequestBody @Valid FlightInsertForm form) {
        flightServ.create(form);
    }

}
