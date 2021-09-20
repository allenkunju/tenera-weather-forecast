package com.tenera.weather.service;

import com.tenera.weather.domain.model.ForecastHistory;
import com.tenera.weather.domain.repository.ForecastHistoryRepository;
import com.tenera.weather.dto.entity.EnumRainyWeather;
import com.tenera.weather.dto.response.Forecast;
import com.tenera.weather.dto.response.ForecastHistoryResponse;
import com.tenera.weather.dto.response.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class WeatherForecastService {

    private final ForecastHistoryRepository historyRepository;

    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String appId;

    public WeatherForecastService(ForecastHistoryRepository historyRepository, RestTemplate restTemplate) {
        this.historyRepository = historyRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Provides the current Weather Forecast
     * @param location  location 'Berlin'/ 'Berlin,de'
     * @return Forecast for the provided location
     */
    public Forecast getForecast(String location) {
        WeatherApiResponse apiResponse = restTemplate.getForObject(
                "https://api.openweathermap.org/data/2.5/weather?q="
                + location + "&units=metric&appid=" + appId, WeatherApiResponse.class);

        if ("200".equals(apiResponse.getCod())) {
            EnumRainyWeather weatherCondition = null;
            try {
                weatherCondition = EnumRainyWeather.valueOf(apiResponse.getWeatherCondition());
            } catch (IllegalArgumentException e) {
                // not a rainy weather
            }
            boolean umbrellaRequired = weatherCondition != null;
            Forecast forecast = Forecast.builder().temp(apiResponse.getTemp()).pressure(apiResponse.getPressure()).umbrella(umbrellaRequired).build();
            historizeForecast(forecast, apiResponse.getName());
            return forecast;
        } else {
            throw new EntityNotFoundException(apiResponse.getMessage());
        }
    }

    /**
     * saves the current query to history
     * @param forecast  forecast to historize
     * @param location  location name
     */
    private void historizeForecast(Forecast forecast, String location) {
        ForecastHistory history = new ForecastHistory();
        history.setLocation(location);
        history.setTemp(forecast.getTemp());
        history.setPressure(forecast.getPressure());
        history.setUmbrella(forecast.isUmbrella());
        history.setTimestamp(Calendar.getInstance().getTime());
        historyRepository.save(history);
    }

    /**
     * Provides the history of last 5 Weather Forecast queries for the location
     * @param location  location 'Berlin'/ 'Berlin,de'
     * @return Forecast History for the provided location
     */
    public ForecastHistoryResponse getForecastHistory(String location) {
        List<ForecastHistory> histories = historyRepository.findTop5ByLocationOrderByTimestampDesc(location);
        if (histories.isEmpty())
            return null;
        double avgTemp = histories.stream().map(ForecastHistory :: getTemp).reduce(0.0, Double::sum) / histories.size();
        int avgPressure = histories.stream().map(ForecastHistory :: getPressure).reduce(0, Integer::sum) / histories.size();
        List<Forecast> forecasts = histories.stream().map(h -> Forecast.builder().temp(h.getTemp()).pressure(h.getPressure()).umbrella(h.isUmbrella()).build()).collect(Collectors.toList());
        return ForecastHistoryResponse.builder().avg_temp(avgTemp).avg_pressure(avgPressure).history(forecasts).build();
    }
}
