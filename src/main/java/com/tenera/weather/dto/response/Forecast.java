package com.tenera.weather.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Forecast {
    int temp;
    int pressure;
    boolean umbrella;
}
