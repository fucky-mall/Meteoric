package com.yash.WeatherInfo.repository;

import com.yash.WeatherInfo.model.LatLonData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LatLonRepository extends JpaRepository<LatLonData, Long> {
    Optional<LatLonData> findByPincode(long pincode);
}
