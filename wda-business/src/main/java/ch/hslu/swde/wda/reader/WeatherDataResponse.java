package ch.hslu.swde.wda.reader;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.domain.WeatherData;
import ch.hslu.swde.wda.utils.DataUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataResponse {
    private static final Logger log = LoggerFactory.getLogger(DataUpdater.class);

    public static List<WeatherData> getWeatherDataResponse(City[] cities) {
        List<WeatherData> consumedWeatherData = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            City city = cities[i];
            String restWebservice = "http://swde.el.eee.intern:8080/weatherdata-rws-provider/rest/weatherdata/" + city.getName();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<WeatherData>> weatherDataResponse = restTemplate.exchange(
                    restWebservice,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<WeatherData>>(){});
            consumedWeatherData.addAll(weatherDataResponse.getBody());
            log.info("Got external data for city: " + city.getName());
        }
        return consumedWeatherData;
    }
}
