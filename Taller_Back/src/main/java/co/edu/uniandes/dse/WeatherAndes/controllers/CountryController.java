package co.edu.uniandes.dse.WeatherAndes.controllers;

import co.edu.uniandes.dse.WeatherAndes.dto.CountryDTO;
import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.services.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CountryDTO> getCountries() {
        return countryService.getCountries().stream()
                .map(c -> modelMapper.map(c, CountryDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CountryDTO getCountry(@PathVariable Long id) throws EntityNotFoundException {
        return modelMapper.map(countryService.getCountry(id), CountryDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CountryDTO createCountry(@RequestBody CountryDTO dto) throws IllegalOperationException {
        CountryEntity created = countryService.createCountry(modelMapper.map(dto, CountryEntity.class));
        return modelMapper.map(created, CountryDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CountryDTO updateCountry(@PathVariable Long id, @RequestBody CountryDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        CountryEntity updated = countryService.updateCountry(id, modelMapper.map(dto, CountryEntity.class));
        return modelMapper.map(updated, CountryDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCountry(@PathVariable Long id) throws EntityNotFoundException {
        countryService.deleteCountry(id);
    }
}
