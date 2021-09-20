package com.tenera.weather.domain.repository;

import com.tenera.weather.domain.model.ForecastHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ForecastHistoryRepository extends JpaRepository<ForecastHistory, String> {

    List<ForecastHistory> findTop5ByLocationOrderByTimestampDesc(String location);
}
