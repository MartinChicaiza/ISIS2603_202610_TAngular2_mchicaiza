package co.edu.uniandes.dse.WeatherAndes.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class CountryEntity extends BaseEntity {
    private String name;
    private String isoCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<CityEntity> cities;
}
