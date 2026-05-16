package co.edu.uniandes.dse.WeatherAndes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherRecordDTO {
    private Long id;
    private double tempC;
    private String condition;
    private int humidity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime recordedAt;
}
