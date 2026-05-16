package co.edu.uniandes.dse.WeatherAndes.repositories;

import co.edu.uniandes.dse.WeatherAndes.entities.WeatherRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRecordRepository extends JpaRepository<WeatherRecordEntity, Long> {
    List<WeatherRecordEntity> findByCityIdOrderByRecordedAtDesc(Long cityId);
}
