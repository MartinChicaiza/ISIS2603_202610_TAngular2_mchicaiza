package co.edu.uniandes.dse.WeatherAndes.dto;

import lombok.Data;

@Data
public class CityDTO {
    private Long id;
    private String name;
    private CountryDTO country;
}
