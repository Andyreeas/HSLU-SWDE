package ch.hslu.swde.wda.service;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.interfaces.CityService;
import ch.hslu.swde.wda.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    public City createCity(City city){
        return cityRepository.save(city);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public List<String> getAllCityNames() {
        List<City> cities = cityRepository.findAll();
        List<String> cityNames = new ArrayList<>();
        for (City city : cities) {
            cityNames.add(city.getName());
        }
        return cityNames;
    }

}
