package com.yash.WeatherInfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataResource {
    private String name;
    private LocalDate date;
    private MainDataResource main;
    private List<WeatherResource> weather;
    private WindResource wind;
    
}