package com.tenera.weather.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponse {

    int id;
    String name;
    Main main;
    String cod;
    String message;
    List<Weather> weather;

    public double getTemp() { return main.temp;}
    public int getPressure() { return main.pressure; }
    public String getWeatherCondition() { return weather.get(0).main; }
}

@Data
class Weather {
    String main;
}

@Data
class Main {
    double temp;
    int pressure;
}
