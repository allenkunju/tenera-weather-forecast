package com.tenera.weather.controller;

import com.tenera.weather.dto.response.Forecast;
import com.tenera.weather.dto.response.ForecastHistoryResponse;
import com.tenera.weather.service.WeatherForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class WeatherForecastController {

    private WeatherForecastService forecastService;

    public WeatherForecastController(WeatherForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/current")
    public ResponseEntity getCurrentForecast(@RequestParam("location") String location) {
        Forecast forecast;
        try {
            forecast = forecastService.getForecast(location);
            return ResponseEntity.ok(forecast);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity getForecastHistory(@RequestParam("location") String location) {
        ForecastHistoryResponse forecastHistory;
        try {
            forecastHistory = forecastService.getForecastHistory(location);
            return ResponseEntity.ok(forecastHistory);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
