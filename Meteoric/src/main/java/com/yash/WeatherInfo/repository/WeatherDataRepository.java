package com.yash.WeatherInfo.repository;

import com.yash.WeatherInfo.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    @Query(value = "select * from weather_data w where w.date=?1 and w.pincode=?2", nativeQuery = true)
    Optional<WeatherData> findByDateAndPincode(LocalDate date, long pincode);
}
