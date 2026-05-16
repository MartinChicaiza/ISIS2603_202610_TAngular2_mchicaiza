package co.edu.uniandes.dse.WeatherAndes.services;

import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<CountryEntity> getCountries() {
        return countryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CountryEntity getCountry(Long id) throws EntityNotFoundException {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " not found"));
    }

    @Transactional
    public CountryEntity createCountry(CountryEntity country) throws IllegalOperationException {
        if (country.getName() == null || country.getName().isBlank())
            throw new IllegalOperationException("Country name cannot be empty");
        if (country.getIsoCode() == null || country.getIsoCode().isBlank())
            throw new IllegalOperationException("Country isoCode cannot be empty");
        if (countryRepository.findByIsoCode(country.getIsoCode()).isPresent())
            throw new IllegalOperationException("A country with isoCode '" + country.getIsoCode() + "' already exists");
        return countryRepository.save(country);
    }

    @Transactional
    public CountryEntity updateCountry(Long id, CountryEntity country) throws EntityNotFoundException, IllegalOperationException {
        CountryEntity existing = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country with id " + id + " not found"));
        if (country.getName() == null || country.getName().isBlank())
            throw new IllegalOperationException("Country name cannot be empty");
        if (country.getIsoCode() == null || country.getIsoCode().isBlank())
            throw new IllegalOperationException("Country isoCode cannot be empty");
        Optional<CountryEntity> byIsoCode = countryRepository.findByIsoCode(country.getIsoCode());
        if (byIsoCode.isPresent() && !byIsoCode.get().getId().equals(id))
            throw new IllegalOperationException("A country with isoCode '" + country.getIsoCode() + "' already exists");
        existing.setName(country.getName());
        existing.setIsoCode(country.getIsoCode());
        return countryRepository.save(existing);
    }

    @Transactional
    public void deleteCountry(Long id) throws EntityNotFoundException {
        if (!countryRepository.existsById(id))
            throw new EntityNotFoundException("Country with id " + id + " not found");
        countryRepository.deleteById(id);
    }
}
