package com.tenera.weather;

import com.tenera.weather.domain.repository.ForecastHistoryRepository;
import com.tenera.weather.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherApplication {

    @Autowired
    private ForecastHistoryRepository historyRepository;

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WeatherForecastService weatherForecastService() { return new WeatherForecastService(historyRepository, restTemplate); }
}
