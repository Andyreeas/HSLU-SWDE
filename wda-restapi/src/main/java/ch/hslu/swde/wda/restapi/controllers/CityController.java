package ch.hslu.swde.wda.restapi.controllers;

import ch.hslu.swde.wda.domain.City;
import ch.hslu.swde.wda.service.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    @Autowired
    private CityServiceImpl cityService;
    
    //A01 - City Data
    @GetMapping("/cities")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }
}
