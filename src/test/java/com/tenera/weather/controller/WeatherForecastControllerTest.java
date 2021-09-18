package com.tenera.weather.controller;

import com.tenera.weather.dto.response.Forecast;
import com.tenera.weather.dto.response.ForecastHistory;
import com.tenera.weather.service.WeatherForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherForecastService service;

    @Test
    public void getCurrentForecast() throws Exception {
        when(service.getForecast(anyString())).thenReturn(Forecast.builder().temp(6).pressure(1000).umbrella(true).build());

        this.mockMvc.perform(get("/current?location=Berlin")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"umbrella\": true")));
    }

    @Test
    public void getCurrentForecastNotFound() throws Exception {
        when(service.getForecast(anyString())).thenThrow(new EntityNotFoundException("Not Found."));

        this.mockMvc.perform(get("/current?location=Zootopia")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void getForecastHistory() throws Exception {
        List<Forecast> history = new ArrayList<>();
        history.add(Forecast.builder().temp(6).pressure(1000).umbrella(true).build());
        when(service.getForecastHistory(anyString())).thenReturn(ForecastHistory.builder().avg_temp(7).avg_pressure(1250).history(history).build());

        this.mockMvc.perform(get("/current?location=Berlin")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"avg_pressure\": 1250")));
    }

}