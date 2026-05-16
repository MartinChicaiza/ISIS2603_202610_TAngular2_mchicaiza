package co.edu.uniandes.dse.WeatherAndes.controllers;

import co.edu.uniandes.dse.WeatherAndes.dto.CityDTO;
import co.edu.uniandes.dse.WeatherAndes.dto.CityDetailDTO;
import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CityDTO> getCities() {
        return cityService.getCities().stream()
                .map(c -> modelMapper.map(c, CityDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDetailDTO getCity(@PathVariable Long id) throws EntityNotFoundException {
        return modelMapper.map(cityService.getCity(id), CityDetailDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CityDTO updateCity(@PathVariable Long id, @RequestBody CityDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        CityEntity updated = cityService.updateCity(id, modelMapper.map(dto, CityEntity.class));
        return modelMapper.map(updated, CityDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Long id) throws EntityNotFoundException {
        cityService.deleteCity(id);
    }
}
