package ch.hslu.swde.wda.restapi.controllers;

import ch.hslu.swde.wda.domain.WeatherData;
import ch.hslu.swde.wda.service.WeatherDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class WeatherDataController {

    @Autowired
    private WeatherDataServiceImpl weatherDataService;
    
    @GetMapping("weatherdata/{cityName}")
    public List<WeatherData> getWeatherDataByCityName
            (@PathVariable String cityName)  {

        return weatherDataService.getWeatherDataByCityName(cityName);
    }
     
    //A02 - WeatherData from last 24 hours
    @GetMapping("weatherdata/{cityName}/last24hours")
    public List<WeatherData> getWeatherDataFromLast24HoursAndByCityName
            (@PathVariable String cityName)  {

        return weatherDataService.getWeatherDataFromLast24HoursByCityName(cityName);
    }
    
    //A03 - Weatherdata during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/during")
    public List<WeatherData> getWeatherDataDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getWeatherDataBetweenPeriodByCityName(cityName, fromDate, toDate);
    }
    //A04 - Average Temperature during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/temperature")
    public Double getTemperatureDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getAverageTemperatureByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A05 - Min and Max Temperature during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/minmaxtemp")
    public String getMinMaxTemperatureDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getMinMaxTemperatureByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A06 - Average Pressure during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/pressure")
    public int getPressureDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getAveragePressureByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A07 - Min and Max Pressure during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/minmaxpressure")
    public String getMinMaxPressureDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getMinMaxPressureByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A08 - Average Humidity during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/humidity")
    public int getHumidityDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getAverageHumidityByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A09 - Min and Max Humidity during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/minmaxhumidity")
    public String getMinMaxHumidityDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getMinMaxHumidityByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A10 - Temperature History during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/temphistory")
    public List<Double> getTemperatureHistoryDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getTemperatureHistoryByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A11 - Humidity History during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/humidityhistory")
    public List<Integer> getHumidityHistoryDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getHumidityHistoryByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
    //A12 - Pressure History during a specific timeperiod
    @GetMapping("weatherdata/{cityName}/pressurehistory")
    public List<Integer> getPressureHistoryDuring(
            @PathVariable String cityName,
            @RequestParam(value = "fromDate") @DateTimeFormat(pattern="ddMMyyyy") Date fromDate,
            @RequestParam(value = "toDate") @DateTimeFormat(pattern="ddMMyyyy") Date toDate) {

        return weatherDataService.getPressureHistoryByCityNameAndBetweenPeriod(cityName, fromDate, toDate);
    }
    
}

