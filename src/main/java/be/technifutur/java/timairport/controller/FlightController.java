package be.technifutur.java.timairport.controller;

import be.technifutur.java.timairport.model.dto.FlightDTO;
import be.technifutur.java.timairport.model.form.FlightInsertForm;
import be.technifutur.java.timairport.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightServ;

    public FlightController(FlightService flightServ) {
        this.flightServ = flightServ;
    }

    @GetMapping("/all")
    public List<FlightDTO> getAll() {
        return flightServ.getAll();
    }

    @GetMapping("/create")
    public void create(Model model) {
        model.addAttribute("flightForm", new FlightInsertForm());
    }

    @PostMapping("/create")
    public void create(@RequestBody @Valid FlightInsertForm form) {
        flightServ.create(form);
    }

    @PostMapping("/{id:[0-9]+}/update")
    public void update(@PathVariable long id, @RequestBody @Valid FlightInsertForm form) {
        flightServ.update(id, form);
    }

    @DeleteMapping("/{id:[0-9]+}/delete")
    public void delete(@PathVariable long id) {
        flightServ.softDelete(id);
    }

    @GetMapping("/read")
    public List<FlightDTO> read() {
        return flightServ.read();
    }

}
