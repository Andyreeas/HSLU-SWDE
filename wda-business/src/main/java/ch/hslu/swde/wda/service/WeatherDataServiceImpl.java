package ch.hslu.swde.wda.service;

import ch.hslu.swde.wda.domain.WeatherData;
import ch.hslu.swde.wda.interfaces.WeatherDataService;
import ch.hslu.swde.wda.repository.WeatherDataRepository;
import ch.hslu.swde.wda.utils.DataMapCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    public WeatherData createWeatherData(WeatherData weatherData){
        Map<String, String> data = DataMapCreator.getDataAsMapFromObject(weatherData);
        Double currentTemperatureCelsius = Double.parseDouble(data.get("CURRENT_TEMPERATURE_CELSIUS"));
        weatherData.setCurrentTemperatureCelsius(currentTemperatureCelsius);
        weatherData.setWeatherSummary(data.get("WEATHER_SUMMARY"));
        weatherData.setWeatherDescription(data.get("WEATHER_DESCRIPTION"));
        int pressure = Integer.parseInt(data.get("PRESSURE"));
        weatherData.setPressure(pressure);
        int humidity = Integer.parseInt(data.get("HUMIDITY"));
        weatherData.setHumidity(humidity);
        Double longitude = Double.parseDouble(data.get("LONGITUDE"));
        weatherData.setLongitude(longitude);
        Double latitude = Double.parseDouble(data.get("LATIUDE"));
        weatherData.setLatitude(latitude);
        weatherData.setCityName(data.get("CITY_NAME"));
        int stationId = Integer.parseInt(data.get("STATION_ID"));
        weatherData.setStationId(stationId);
        weatherData.setCountry(data.get("COUNTRY"));
        weatherData.setWindDirection(data.get("WIND_DIRECTION"));
        Double windSpeed = Double.parseDouble(data.get("WIND_SPEED"));
        weatherData.setWindSpeed(windSpeed);
        LocalDateTime lastUpdatedTime = LocalDateTime.parse(data.get("LAST_UPDATE_TIME"));
        weatherData.setLastUpdatedWeather(lastUpdatedTime);
        return weatherDataRepository.save(weatherData);
        
    }
    
    public List<WeatherData> getWeatherDataByCityName(String cityName) {
        return weatherDataRepository.findWeatherDataByCityName(cityName);
    }

    public List<WeatherData> getWeatherDataFromLast24HoursByCityName(String cityName) {
        LocalDateTime minusOneDay = LocalDateTime.now().minusDays(1);
        return weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherAfter(cityName, minusOneDay);
    }

    public List<WeatherData> getWeatherDataBetweenPeriodByCityName(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
    }

    public Double getAverageTemperatureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        Double averageTemperature = 0.0;
        for (WeatherData myWeather : myWeatherData) {
            averageTemperature += myWeather.getCurrentTemperatureCelsius();
        }
        return averageTemperature / myWeatherData.size();
    }
    
    
    public String getMinMaxTemperatureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
       
        Collections.sort(
                myWeatherData,
                (weather1, weather2) -> Double.compare(weather1.getCurrentTemperatureCelsius(),
                        weather2.getCurrentTemperatureCelsius()));
        double min = myWeatherData.get(0).getCurrentTemperatureCelsius();
        double max = myWeatherData.get(myWeatherData.size() - 1).getCurrentTemperatureCelsius();
        return "Min: " + min + " Max: " + max;
    }
    
    
    public int getAveragePressureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        int averagePressure = 0;
        for (WeatherData myWeather : myWeatherData) {
            averagePressure += myWeather.getPressure();
        }
        return averagePressure / myWeatherData.size();
    }
    
    
    public String getMinMaxPressureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        
        Collections.sort(
                myWeatherData,
                (weather1, weather2) -> Double.compare(weather1.getPressure(),
                        weather2.getPressure()));
        int min = myWeatherData.get(0).getPressure();
        int max = myWeatherData.get(myWeatherData.size() - 1).getPressure();
        return "Min: " + min + " Max: " + max;
    }
    
    
     public int getAverageHumidityByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        int averageHumidity = 0;
        for (WeatherData myWeather : myWeatherData) {
            averageHumidity += myWeather.getHumidity();
        }
        return averageHumidity / myWeatherData.size();
    }
     
     
    public String getMinMaxHumidityByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        
        Collections.sort(
                myWeatherData,
                (weather1, weather2) -> Double.compare(weather1.getHumidity(),
                        weather2.getHumidity()));
        int min = myWeatherData.get(0).getHumidity();
        int max = myWeatherData.get(myWeatherData.size() - 1).getHumidity();
        return "Min: " + min + " Max: " + max;
    }
    
    
    public List<Double> getTemperatureHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        
        List<Double> temphistory = new ArrayList<>();
        for (WeatherData myWeather : myWeatherData) {
            temphistory.add(myWeather.getCurrentTemperatureCelsius());
        }
        return temphistory;
    }
    
    
    public List<Integer> getHumidityHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        
        List<Integer> humidityhistory = new ArrayList<>();
        for (WeatherData myWeather : myWeatherData) {
            humidityhistory.add(myWeather.getHumidity());
        }
        return humidityhistory;
    }
    
    
    public List<Integer> getPressureHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate){
        LocalDateTime myFromDate = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime myToDate = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<WeatherData> myWeatherData = weatherDataRepository.findWeatherDataByCityNameAndLastUpdatedWeatherBetween(cityName, myFromDate, myToDate);
        
        List<Integer> pressurehistory = new ArrayList<>();
        for (WeatherData myWeather : myWeatherData) {
            pressurehistory.add(myWeather.getPressure());
        }
        return pressurehistory;
    }
    
    
    
    
    
    
    
}
