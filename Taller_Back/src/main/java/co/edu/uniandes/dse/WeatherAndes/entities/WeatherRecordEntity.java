package co.edu.uniandes.dse.WeatherAndes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class WeatherRecordEntity extends BaseEntity {
    private double tempC;
    private String condition;
    private int humidity;
    private LocalDateTime recordedAt;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CityEntity city;
}
