package co.edu.uniandes.dse.WeatherAndes.services;

import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.repositories.CityRepository;
import co.edu.uniandes.dse.WeatherAndes.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CityEntity> getCities() {
        return cityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CityEntity> getCitiesByCountry(Long countryId) throws EntityNotFoundException {
        if (!countryRepository.existsById(countryId))
            throw new EntityNotFoundException("Country with id " + countryId + " not found");
        return cityRepository.findByCountryId(countryId);
    }

    @Transactional(readOnly = true)
    public CityEntity getCity(Long id) throws EntityNotFoundException {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id " + id + " not found"));
    }

    @Transactional
    public CityEntity createCity(Long countryId, CityEntity city) throws EntityNotFoundException, IllegalOperationException {
        CountryEntity country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + countryId + " not found"));
        if (city.getName() == null || city.getName().isBlank())
            throw new IllegalOperationException("City name cannot be empty");
        if (cityRepository.findByNameAndCountryId(city.getName(), countryId).isPresent())
            throw new IllegalOperationException("A city named '" + city.getName() + "' already exists in this country");
        city.setCountry(country);
        return cityRepository.save(city);
    }

    @Transactional
    public CityEntity updateCity(Long id, CityEntity city) throws EntityNotFoundException, IllegalOperationException {
        CityEntity existing = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id " + id + " not found"));
        if (city.getName() == null || city.getName().isBlank())
            throw new IllegalOperationException("City name cannot be empty");
        Long countryId = existing.getCountry().getId();
        Optional<CityEntity> conflict = cityRepository.findByNameAndCountryId(city.getName(), countryId);
        if (conflict.isPresent() && !conflict.get().getId().equals(id))
            throw new IllegalOperationException("A city named '" + city.getName() + "' already exists in this country");
        existing.setName(city.getName());
        return cityRepository.save(existing);
    }

    @Transactional
    public void deleteCity(Long id) throws EntityNotFoundException {
        if (!cityRepository.existsById(id))
            throw new EntityNotFoundException("City with id " + id + " not found");
        cityRepository.deleteById(id);
    }
}
