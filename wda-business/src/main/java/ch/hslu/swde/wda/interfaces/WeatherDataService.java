package ch.hslu.swde.wda.interfaces;

import ch.hslu.swde.wda.domain.WeatherData;

import java.util.Date;
import java.util.List;

public interface WeatherDataService {
    WeatherData createWeatherData(WeatherData weatherData);
    List<WeatherData> getWeatherDataByCityName(String cityName);
    List<WeatherData> getWeatherDataFromLast24HoursByCityName(String cityName);
    List<WeatherData> getWeatherDataBetweenPeriodByCityName(String cityName, Date fromDate, Date toDate);
    Double getAverageTemperatureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    String getMinMaxTemperatureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    int getAveragePressureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    String getMinMaxPressureByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    int getAverageHumidityByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    String getMinMaxHumidityByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    List<Double> getTemperatureHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    List<Integer> getHumidityHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
    List<Integer> getPressureHistoryByCityNameAndBetweenPeriod(String cityName, Date fromDate, Date toDate);
}
