package com.yash.WeatherInfo.model;

import com.yash.WeatherInfo.dto.LatLonResource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LatLonData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double lat;

    private double lon;

    private long pincode;

    public static LatLonResource toResponse(LatLonData latLonData) {
        return LatLonResource.builder()
                .lat(latLonData.lat)
                .lon(latLonData.lon)
                .zip(latLonData.getPincode())
                .build();
    }

    public static LatLonData toEntity(LatLonResource latLonResource) {
        return LatLonData.builder()
                .lat(latLonResource.getLat())
                .lon(latLonResource.getLon())
                .pincode(latLonResource.getZip())
                .build();
    }
}
