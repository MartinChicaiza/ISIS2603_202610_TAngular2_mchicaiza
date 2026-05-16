package co.edu.uniandes.dse.WeatherAndes.services;

import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import co.edu.uniandes.dse.WeatherAndes.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.WeatherAndes.exceptions.IllegalOperationException;
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
@Import(CountryService.class)
class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    private final PodamFactory factory = new PodamFactoryImpl();
    private final List<CountryEntity> countryList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        countryRepository.deleteAll();
        countryList.clear();
        for (int i = 0; i < 3; i++) {
            CountryEntity country = factory.manufacturePojo(CountryEntity.class);
            country.setCities(null);
            countryRepository.save(country);
            countryList.add(country);
        }
    }

    @Test
    void testGetCountries() {
        List<CountryEntity> result = countryService.getCountries();
        assertEquals(countryList.size(), result.size());
    }

    @Test
    void testGetCountry() throws EntityNotFoundException {
        CountryEntity expected = countryList.get(0);
        CountryEntity result = countryService.getCountry(expected.getId());
        assertNotNull(result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getIsoCode(), result.getIsoCode());
    }

    @Test
    void testGetCountryNotFound() {
        assertThrows(EntityNotFoundException.class, () -> countryService.getCountry(0L));
    }

    @Test
    void testCreateCountry() throws IllegalOperationException {
        CountryEntity newCountry = factory.manufacturePojo(CountryEntity.class);
        newCountry.setCities(null);
        CountryEntity result = countryService.createCountry(newCountry);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(newCountry.getName(), result.getName());
        assertEquals(newCountry.getIsoCode(), result.getIsoCode());
    }

    @Test
    void testCreateCountryEmptyName() {
        CountryEntity country = factory.manufacturePojo(CountryEntity.class);
        country.setCities(null);
        country.setName("");
        assertThrows(IllegalOperationException.class, () -> countryService.createCountry(country));
    }

    @Test
    void testCreateCountryNullName() {
        CountryEntity country = factory.manufacturePojo(CountryEntity.class);
        country.setCities(null);
        country.setName(null);
        assertThrows(IllegalOperationException.class, () -> countryService.createCountry(country));
    }

    @Test
    void testCreateCountryEmptyIsoCode() {
        CountryEntity country = factory.manufacturePojo(CountryEntity.class);
        country.setCities(null);
        country.setIsoCode("");
        assertThrows(IllegalOperationException.class, () -> countryService.createCountry(country));
    }

    @Test
    void testCreateCountryNullIsoCode() {
        CountryEntity country = factory.manufacturePojo(CountryEntity.class);
        country.setCities(null);
        country.setIsoCode(null);
        assertThrows(IllegalOperationException.class, () -> countryService.createCountry(country));
    }

    @Test
    void testCreateCountryDuplicateIsoCode() {
        CountryEntity country = factory.manufacturePojo(CountryEntity.class);
        country.setCities(null);
        country.setIsoCode(countryList.get(0).getIsoCode());
        assertThrows(IllegalOperationException.class, () -> countryService.createCountry(country));
    }

    @Test
    void testUpdateCountry() throws EntityNotFoundException, IllegalOperationException {
        CountryEntity existing = countryList.get(0);
        CountryEntity update = factory.manufacturePojo(CountryEntity.class);
        update.setCities(null);
        CountryEntity result = countryService.updateCountry(existing.getId(), update);
        assertNotNull(result);
        assertEquals(update.getName(), result.getName());
        assertEquals(update.getIsoCode(), result.getIsoCode());
    }

    @Test
    void testUpdateCountryNotFound() {
        CountryEntity update = factory.manufacturePojo(CountryEntity.class);
        update.setCities(null);
        assertThrows(EntityNotFoundException.class, () -> countryService.updateCountry(0L, update));
    }

    @Test
    void testUpdateCountryEmptyName() {
        CountryEntity existing = countryList.get(0);
        CountryEntity update = factory.manufacturePojo(CountryEntity.class);
        update.setCities(null);
        update.setName("");
        assertThrows(IllegalOperationException.class, () -> countryService.updateCountry(existing.getId(), update));
    }

    @Test
    void testUpdateCountryDuplicateIsoCode() {
        CountryEntity existing = countryList.get(0);
        CountryEntity update = factory.manufacturePojo(CountryEntity.class);
        update.setCities(null);
        update.setIsoCode(countryList.get(1).getIsoCode());
        assertThrows(IllegalOperationException.class, () -> countryService.updateCountry(existing.getId(), update));
    }

    @Test
    void testDeleteCountry() throws EntityNotFoundException {
        CountryEntity country = countryList.get(0);
        countryService.deleteCountry(country.getId());
        assertThrows(EntityNotFoundException.class, () -> countryService.getCountry(country.getId()));
    }

    @Test
    void testDeleteCountryNotFound() {
        assertThrows(EntityNotFoundException.class, () -> countryService.deleteCountry(0L));
    }
}
