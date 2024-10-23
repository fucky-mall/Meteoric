package com.yash.WeatherInfo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainDataResource {
    private float temp;
    private float temp_min;
    private float temp_max;
    private int pressure;
    private int humidity;
}
