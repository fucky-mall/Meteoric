package com.yash.WeatherInfo.repository;

import com.yash.WeatherInfo.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Repository
@Slf4j
public class CacheRepository {

    public static final Integer USER_CACHE_KEY_EXPIRY = 500;

    @Autowired
    RedisTemplate<String, WeatherData> redisTemplate;

    public void set(WeatherData weatherData) {
        String key = getKey(weatherData.getPincode(), weatherData.getDate());

        try{
            redisTemplate.opsForValue().set(key, weatherData, USER_CACHE_KEY_EXPIRY, TimeUnit.SECONDS);
            log.info("Cached WeatherData with key: {}", key);
        }catch (Exception e) {
            log.error("Error caching WeatherData with key: {}", key, e);
        }
    }

    public WeatherData get(long pincode, LocalDate date) {
        String key = getKey(pincode, date);
        try {
            WeatherData weatherData = redisTemplate.opsForValue().get(key);
            if (weatherData == null) {
                log.info("WeatherData not found in cache for key: {}", key);
            } else {
                log.info("Retrieved WeatherData from cache with key: {}", key);
            }
            return weatherData;
        } catch (Exception e) {
            log.error("Error retrieving WeatherData from cache with key: {}", key, e);
            return null;
        }
    }

    public String getKey(Long pincode, LocalDate date) {
        return pincode + date.toString();
    }
}
