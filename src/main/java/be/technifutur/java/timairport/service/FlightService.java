package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.model.dto.FlightDTO;
import be.technifutur.java.timairport.model.form.FlightInsertForm;

import java.util.List;

public interface FlightService {

    void create(FlightInsertForm form);

    void update(long id, FlightInsertForm form);

    void softDelete(long id);

    List<FlightDTO> getAll();

    List<FlightDTO> read();
}
