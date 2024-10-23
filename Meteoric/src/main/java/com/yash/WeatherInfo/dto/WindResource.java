package com.yash.WeatherInfo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WindResource {
    private double speed;
    private int deg;
    private double gust;
}
