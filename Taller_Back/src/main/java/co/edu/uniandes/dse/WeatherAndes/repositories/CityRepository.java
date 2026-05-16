package co.edu.uniandes.dse.WeatherAndes.repositories;

import co.edu.uniandes.dse.WeatherAndes.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    List<CityEntity> findByCountryId(Long countryId);
    Optional<CityEntity> findByNameAndCountryId(String name, Long countryId);
}
