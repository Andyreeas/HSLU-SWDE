package ch.hslu.swde.wda.utils;

import ch.hslu.swde.wda.domain.WeatherData;

import java.util.HashMap;
import java.util.Map;

public class DataMapCreator {
    public static Map<String, String> getDataAsMapFromObject(WeatherData weatherData){
        String data = weatherData.getData();
        Map<String, String> map = new HashMap<>();

        String[] dataArray = data.split("#");
        for (String part : dataArray) {
            int indexOfSeparator = part.indexOf(":");
            String key = part.substring(0, indexOfSeparator);
            String value = part.substring(indexOfSeparator+1, part.length());
            map.put(key, value);
        }
        return map;
    }
}
