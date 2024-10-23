package com.yash.WeatherInfo.config;

import com.yash.WeatherInfo.dto.LatLonResource;
import com.yash.WeatherInfo.dto.WeatherDataResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WeatherApiClient {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public LatLonResource getLatLon(long pincode) {
        String url = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=" + apiKey;

        //Asynchronous Call
        WebClient.Builder builder = WebClient.builder();
        return builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(LatLonResource.class)
                .block();
    }

    public WeatherDataResource getWeatherData(double lat, double lon) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +"&lon=" + lon + "&appid=" + apiKey;
        WebClient.Builder builder = WebClient.builder();
        return builder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(WeatherDataResource.class)
                .block();
    }
}
