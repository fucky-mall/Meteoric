package com.yash.WeatherInfo;

import com.yash.WeatherInfo.dto.WeatherDataResource;
import com.yash.WeatherInfo.model.*;
import com.yash.WeatherInfo.repository.CacheRepository;
import com.yash.WeatherInfo.repository.LatLonRepository;
import com.yash.WeatherInfo.repository.WeatherDataRepository;
import com.yash.WeatherInfo.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherInfoApplicationTests {
    @Mock
    private LatLonRepository latLonRepository;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private CacheRepository cacheRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testGetWeatherData_Success() {
        // Given
        long pincode = 411014;
        LocalDate date = LocalDate.of(2020, 10, 15);
        LatLonData latLonData = LatLonData.builder()
                .lat(18.5204)
                .lon(73.8567)
                .build();
        WeatherData weatherData = WeatherData.builder()
                .pincode(pincode)
                .date(date)
                .name("Pune")
                .main(MainData.builder().build())
                .weather(List.of())
                .wind(Wind.builder().build())
                .build();

        when(cacheRepository.get(pincode, date)).thenReturn(null);
        when(latLonRepository.findByPincode(pincode)).thenReturn(Optional.of(latLonData));
        when(weatherDataRepository.findByDateAndPincode(date, pincode)).thenReturn(Optional.of(weatherData));

        ResponseEntity<WeatherDataResource> response = ResponseEntity.ok(weatherService.getWeatherData(pincode, date));

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Pune", response.getBody().getName());

        verify(latLonRepository).findByPincode(pincode);
        verify(weatherDataRepository).findByDateAndPincode(date, pincode);
    }
}
