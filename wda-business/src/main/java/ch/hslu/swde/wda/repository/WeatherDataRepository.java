package ch.hslu.swde.wda.repository;

import ch.hslu.swde.wda.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findWeatherDataById(long id);
    List<WeatherData> findWeatherDataByCityName(String cityName);
    List<WeatherData> findWeatherDataByCityNameAndLastUpdatedWeatherAfter(String cityName, LocalDateTime last24hours);
    List<WeatherData> findWeatherDataByCityNameAndLastUpdatedWeatherBetween(String cityName, LocalDateTime fromDate, LocalDateTime toDate);
}
