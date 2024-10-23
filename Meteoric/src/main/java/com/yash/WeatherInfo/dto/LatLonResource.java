package com.yash.WeatherInfo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LatLonResource {
    private double lat;
    private double lon;
    private long zip;
}
