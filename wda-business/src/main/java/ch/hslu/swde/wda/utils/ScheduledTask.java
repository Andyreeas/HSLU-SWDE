package ch.hslu.swde.wda.utils;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.WeatherData;
import ch.hslu.swde.wda.reader.CityResponse;
import ch.hslu.swde.wda.reader.WeatherDataResponse;
import ch.hslu.swde.wda.service.CityServiceImpl;
import ch.hslu.swde.wda.service.WeatherDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(DataUpdater.class);

    @Autowired
    private CityServiceImpl cityService;

    @Autowired
    private WeatherDataServiceImpl weatherDataService;

    @Scheduled(fixedRate = 1000L * 60L * 60L, initialDelay = 1000L * 60L * 60L) // Scheduled to run every hour
    public void startUpdatingData(){
        City[] cities = CityResponse.getCityResponse();
        List<WeatherData> myWeather = WeatherDataResponse.getWeatherDataResponse(cities);

        for (City city : cities) {
            cityService.createCity(city);
        }

        for (WeatherData weatherData : myWeather) {
            weatherDataService.createWeatherData(weatherData);
        }
        log.info("Updated data in database on: " + new Date());
    }
}
