package com.yash.WeatherInfo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherResource {
    private String main;
    private String description;
    private String icon;
}
