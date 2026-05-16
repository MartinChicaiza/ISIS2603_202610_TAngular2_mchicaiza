package co.edu.uniandes.dse.WeatherAndes.services;

import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import co.edu.uniandes.dse.WeatherAndes.entities.WeatherRecordEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.repositories.CityRepository;
import co.edu.uniandes.dse.WeatherAndes.repositories.WeatherRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherRecordService {

    @Autowired
    private WeatherRecordRepository weatherRecordRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<WeatherRecordEntity> findByCityId(Long cityId) throws EntityNotFoundException {
        if (!cityRepository.existsById(cityId))
            throw new EntityNotFoundException("City with id " + cityId + " not found");
        return weatherRecordRepository.findByCityIdOrderByRecordedAtDesc(cityId);
    }

    @Transactional
    public WeatherRecordEntity create(Long cityId, WeatherRecordEntity record) throws EntityNotFoundException {
        CityEntity city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("City with id " + cityId + " not found"));
        record.setCity(city);
        record.setRecordedAt(LocalDateTime.now());
        return weatherRecordRepository.save(record);
    }
}
