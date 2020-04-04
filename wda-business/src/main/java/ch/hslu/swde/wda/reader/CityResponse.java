package ch.hslu.swde.wda.reader;

import ch.hslu.swde.wda.domain.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CityResponse {
    private static final String restWebservice = "http://swde.el.eee.intern:8080/weatherdata-rws-provider/rest/weatherdata/cities";

    public static City[] getCityResponse(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<City[]> cityResponse = restTemplate.getForEntity(restWebservice, City[].class);
        return cityResponse.getBody();
    }
}
