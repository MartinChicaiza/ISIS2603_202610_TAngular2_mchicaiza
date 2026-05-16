package co.edu.uniandes.dse.WeatherAndes.repositories;

import co.edu.uniandes.dse.WeatherAndes.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findByIsoCode(String isoCode);
    Optional<CountryEntity> findByName(String name);
}
