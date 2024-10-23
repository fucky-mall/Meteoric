package com.yash.WeatherInfo.utils;
import com.yash.WeatherInfo.dto.MainDataResource;
import com.yash.WeatherInfo.dto.WeatherDataResource;
import com.yash.WeatherInfo.dto.WeatherResource;
import com.yash.WeatherInfo.dto.WindResource;
import com.yash.WeatherInfo.model.MainData;
import com.yash.WeatherInfo.model.Weather;
import com.yash.WeatherInfo.model.WeatherData;
import com.yash.WeatherInfo.model.Wind;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class Utils {

    public static WeatherData convertToWeatherData(WeatherDataResource resource, long pincode) {
        return WeatherData.builder()
                .pincode(pincode)
                .name(resource.getName())
                .main(MainData.builder()
                        .temp(resource.getMain().getTemp())
                        .temp_min(resource.getMain().getTemp_min())
                        .temp_max(resource.getMain().getTemp_max())
                        .pressure(resource.getMain().getPressure())
                        .humidity(resource.getMain().getHumidity())
                        .build())
                .weather(resource.getWeather().stream()
                        .map(w -> Weather.builder()
                                .main(w.getMain())
                                .description(w.getDescription())
                                .icon(w.getIcon())
                                .build())
                        .collect(Collectors.toList()))
                .wind(Wind.builder()
                        .speed(resource.getWind().getSpeed())
                        .deg(resource.getWind().getDeg())
                        .gust(resource.getWind().getGust())
                        .build())
                .build();
    }

    public static WeatherDataResource convertToWeatherDataResource(WeatherData weatherData) {
        return WeatherDataResource.builder()
                .name(weatherData.getName())
                .date(weatherData.getDate())
                .main(MainDataResource.builder()
                        .temp(weatherData.getMain().getTemp()-273)
                        .temp_max(weatherData.getMain().getTemp_max()-273)
                        .temp_min(weatherData.getMain().getTemp_min()-273)
                        .pressure(weatherData.getMain().getPressure())
                        .humidity(weatherData.getMain().getHumidity())
                        .build())
                .weather(weatherData.getWeather().stream()
                        .map(w -> WeatherResource.builder()
                                .main(w.getMain())
                                .description(w.getDescription())
                                .icon(w.getIcon())
                                .build())
                        .collect(Collectors.toList()))
                .wind(WindResource.builder()
                        .speed(weatherData.getWind().getSpeed())
                        .deg(weatherData.getWind().getDeg())
                        .gust(weatherData.getWind().getGust())
                        .build())
                .build();

    }
}

