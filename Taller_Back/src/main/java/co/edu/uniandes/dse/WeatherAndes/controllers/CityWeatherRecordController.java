package co.edu.uniandes.dse.WeatherAndes.controllers;

import co.edu.uniandes.dse.WeatherAndes.dto.WeatherRecordDTO;
import co.edu.uniandes.dse.WeatherAndes.entities.WeatherRecordEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.services.WeatherRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities/{cityId}/weather-records")
public class CityWeatherRecordController {

    @Autowired
    private WeatherRecordService weatherRecordService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WeatherRecordDTO> getRecords(@PathVariable Long cityId) throws EntityNotFoundException {
        return weatherRecordService.findByCityId(cityId).stream()
                .map(r -> modelMapper.map(r, WeatherRecordDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WeatherRecordDTO createRecord(@PathVariable Long cityId, @RequestBody WeatherRecordDTO dto)
            throws EntityNotFoundException {
        WeatherRecordEntity entity = modelMapper.map(dto, WeatherRecordEntity.class);
        return modelMapper.map(weatherRecordService.create(cityId, entity), WeatherRecordDTO.class);
    }
}
