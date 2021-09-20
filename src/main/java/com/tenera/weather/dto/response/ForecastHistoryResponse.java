package com.tenera.weather.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ForecastHistoryResponse {
    double avg_temp;
    int avg_pressure;
    List<Forecast> history;
}
