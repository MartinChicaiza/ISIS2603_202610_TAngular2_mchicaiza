package co.edu.uniandes.dse.WeatherAndes.services;

import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.WeatherAndes.repositories.CityRepository;
import co.edu.uniandes.dse.WeatherAndes.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(CityService.class)
class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    private final PodamFactory factory = new PodamFactoryImpl();
    private final List<CityEntity> cityList = new ArrayList<>();
    private final List<CountryEntity> countryList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        cityRepository.deleteAll();
        countryRepository.deleteAll();
        cityList.clear();
        countryList.clear();

        for (int i = 0; i < 3; i++) {
            CountryEntity country = factory.manufacturePojo(CountryEntity.class);
            country.setCities(null);
            countryRepository.save(country);
            countryList.add(country);
        }
        for (int i = 0; i < 3; i++) {
            CityEntity city = factory.manufacturePojo(CityEntity.class);
            city.setCountry(countryList.get(0));
            cityRepository.save(city);
            cityList.add(city);
        }
    }

    @Test
    void testGetCities() {
        List<CityEntity> result = cityService.getCities();
        assertEquals(cityList.size(), result.size());
    }

    @Test
    void testGetCitiesByCountry() throws EntityNotFoundException {
        List<CityEntity> result = cityService.getCitiesByCountry(countryList.get(0).getId());
        assertEquals(cityList.size(), result.size());
    }

    @Test
    void testGetCitiesByCountryEmpty() throws EntityNotFoundException {
        List<CityEntity> result = cityService.getCitiesByCountry(countryList.get(1).getId());
        assertEquals(0, result.size());
    }

    @Test
    void testGetCitiesByCountryNotFound() {
        assertThrows(EntityNotFoundException.class, () -> cityService.getCitiesByCountry(0L));
    }

    @Test
    void testGetCity() throws EntityNotFoundException {
        CityEntity expected = cityList.get(0);
        CityEntity result = cityService.getCity(expected.getId());
        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
    }

    @Test
    void testGetCityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> cityService.getCity(0L));
    }

    @Test
    void testCreateCity() throws EntityNotFoundException, IllegalOperationException {
        CityEntity newCity = factory.manufacturePojo(CityEntity.class);
        CountryEntity country = countryList.get(1);
        CityEntity result = cityService.createCity(country.getId(), newCity);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(newCity.getName(), result.getName());
        assertEquals(country.getId(), result.getCountry().getId());
    }

    @Test
    void testCreateCityCountryNotFound() {
        CityEntity city = factory.manufacturePojo(CityEntity.class);
        assertThrows(EntityNotFoundException.class, () -> cityService.createCity(0L, city));
    }

    @Test
    void testCreateCityEmptyName() {
        CityEntity city = factory.manufacturePojo(CityEntity.class);
        city.setName("");
        assertThrows(IllegalOperationException.class,
                () -> cityService.createCity(countryList.get(0).getId(), city));
    }

    @Test
    void testCreateCityNullName() {
        CityEntity city = factory.manufacturePojo(CityEntity.class);
        city.setName(null);
        assertThrows(IllegalOperationException.class,
                () -> cityService.createCity(countryList.get(0).getId(), city));
    }

    @Test
    void testCreateCityDuplicateName() {
        CityEntity existing = cityList.get(0);
        CityEntity duplicate = factory.manufacturePojo(CityEntity.class);
        duplicate.setName(existing.getName());
        assertThrows(IllegalOperationException.class,
                () -> cityService.createCity(countryList.get(0).getId(), duplicate));
    }

    @Test
    void testUpdateCity() throws EntityNotFoundException, IllegalOperationException {
        CityEntity existing = cityList.get(0);
        CityEntity update = factory.manufacturePojo(CityEntity.class);
        CityEntity result = cityService.updateCity(existing.getId(), update);
        assertNotNull(result);
        assertEquals(update.getName(), result.getName());
    }

    @Test
    void testUpdateCityNotFound() {
        CityEntity update = factory.manufacturePojo(CityEntity.class);
        assertThrows(EntityNotFoundException.class, () -> cityService.updateCity(0L, update));
    }

    @Test
    void testUpdateCityEmptyName() {
        CityEntity existing = cityList.get(0);
        CityEntity update = factory.manufacturePojo(CityEntity.class);
        update.setName("");
        assertThrows(IllegalOperationException.class, () -> cityService.updateCity(existing.getId(), update));
    }

    @Test
    void testUpdateCityDuplicateName() {
        CityEntity city1 = cityList.get(0);
        CityEntity city2 = cityList.get(1);
        CityEntity update = factory.manufacturePojo(CityEntity.class);
        update.setName(city2.getName());
        assertThrows(IllegalOperationException.class, () -> cityService.updateCity(city1.getId(), update));
    }

    @Test
    void testDeleteCity() throws EntityNotFoundException {
        CityEntity city = cityList.get(0);
        cityService.deleteCity(city.getId());
        assertThrows(EntityNotFoundException.class, () -> cityService.getCity(city.getId()));
    }

    @Test
    void testDeleteCityNotFound() {
        assertThrows(EntityNotFoundException.class, () -> cityService.deleteCity(0L));
    }
}
