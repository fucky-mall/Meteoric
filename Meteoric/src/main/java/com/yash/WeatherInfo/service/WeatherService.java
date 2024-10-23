package com.yash.WeatherInfo.service;

import com.yash.WeatherInfo.config.WeatherApiClient;
import com.yash.WeatherInfo.dto.LatLonResource;
import com.yash.WeatherInfo.dto.WeatherDataResource;
import com.yash.WeatherInfo.model.LatLonData;
import com.yash.WeatherInfo.model.WeatherData;
import com.yash.WeatherInfo.repository.CacheRepository;
import com.yash.WeatherInfo.repository.LatLonRepository;
import com.yash.WeatherInfo.repository.WeatherDataRepository;
import com.yash.WeatherInfo.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jdk.jshell.execution.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class WeatherService {
    @Autowired
    LatLonRepository latLonRepository;

    @Autowired
    WeatherDataRepository weatherDataRepository;

    @Autowired
    WeatherApiClient weatherApiClient;

    @Autowired
    CacheRepository cacheRepository;

    public WeatherDataResource getWeatherData(long pincode, LocalDate date) {
        WeatherData weatherDataFromCache = cacheRepository.get(pincode, date);
        if(weatherDataFromCache != null) {
            log.info("Returning Weather Data From Cache");
            return Utils.convertToWeatherDataResource(weatherDataFromCache);
        }

        Optional<LatLonData> latLonOptional = latLonRepository.findByPincode(pincode);
        LatLonData latLonData;
        if(latLonOptional.isEmpty()) {
            LatLonResource latLonResource = weatherApiClient.getLatLon(pincode);
            latLonData = LatLonData.toEntity(latLonResource);
            latLonRepository.save(latLonData);
        }
        else latLonData = latLonOptional.get();


        Optional<WeatherData> weatherDataOptional = weatherDataRepository.findByDateAndPincode(date, pincode);
        WeatherData weatherData;

        if(Objects.equals(date, LocalDate.now()) && weatherDataOptional.isEmpty()){
            WeatherDataResource weatherDataResource = weatherApiClient.getWeatherData(latLonData.getLat(), latLonData.getLon());
            weatherData = Utils.convertToWeatherData(weatherDataResource,pincode);
            weatherDataOptional = Optional.of(weatherDataRepository.save(weatherData));
        }
        if (weatherDataOptional.isEmpty()) {
            String errorMessage = "Weather Data Not Found for Date: " + date + " and pincode: " + pincode;
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }

        else weatherData = weatherDataOptional.get();

        cacheRepository.set(weatherData);

        return Utils.convertToWeatherDataResource(weatherData);
    }
}
