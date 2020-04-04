package ch.hslu.swde.wda.interfaces;

import ch.hslu.swde.wda.domain.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City createCity(City city);
}
