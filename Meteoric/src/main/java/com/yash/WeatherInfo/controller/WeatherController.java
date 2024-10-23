package com.yash.WeatherInfo.controller;


import com.yash.WeatherInfo.dto.WeatherDataResource;
import com.yash.WeatherInfo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherDataResource> getCoordinates(@RequestParam("pincode") long pincode,
                                                             @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date) {
        return ResponseEntity.ok(weatherService.getWeatherData(pincode,date));
    }
}
