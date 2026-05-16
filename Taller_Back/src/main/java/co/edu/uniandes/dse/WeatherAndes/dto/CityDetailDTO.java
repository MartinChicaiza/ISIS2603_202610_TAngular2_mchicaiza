package co.edu.uniandes.dse.WeatherAndes.dto;

import lombok.Data;

@Data
public class CityDetailDTO {
    private Long id;
    private String name;
    private CountryDTO country;
}
