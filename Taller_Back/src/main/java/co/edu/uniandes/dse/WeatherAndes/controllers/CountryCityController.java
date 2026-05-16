package co.edu.uniandes.dse.WeatherAndes.controllers;

import co.edu.uniandes.dse.WeatherAndes.dto.CityDTO;
import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries/{countryId}/cities")
public class CountryCityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO createCity(@PathVariable Long countryId, @RequestBody CityDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        CityEntity created = cityService.createCity(countryId, modelMapper.map(dto, CityEntity.class));
        return modelMapper.map(created, CityDTO.class);
    }
}
