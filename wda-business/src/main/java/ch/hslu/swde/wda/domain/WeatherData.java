package ch.hslu.swde.wda.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WeatherData")
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("serial")
public class WeatherData extends AuditModel{
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore
    @JsonProperty("city")
    private City city;

    @Column(columnDefinition = "numeric")
    private Double currentTemperatureCelsius;
    @Column(columnDefinition = "text")
    private String weatherSummary;
    @Column(columnDefinition = "text")
    private String weatherDescription;
    @Column(columnDefinition = "int")
    private int pressure;
    @Column(columnDefinition = "int")
    private int humidity;
    @Column(columnDefinition = "numeric")
    private Double longitude;
    @Column(columnDefinition = "numeric")
    private Double latitude;
    @Column(columnDefinition = "text")
    private String cityName;
    @Column(columnDefinition = "int")
    private int stationId;
    @Column(columnDefinition = "text")
    private String country;
    @Column(columnDefinition = "text")
    private String windDirection;
    @Column(columnDefinition = "numeric")
    private Double windSpeed;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdatedWeather;

    @Column(columnDefinition = "text")
    private String data;

    public WeatherData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Double getCurrentTemperatureCelsius() {
        return currentTemperatureCelsius;
    }

    public void setCurrentTemperatureCelsius(Double currentTemperatureCelsius) {
        this.currentTemperatureCelsius = currentTemperatureCelsius;
    }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getLastUpdatedWeather() {
        return lastUpdatedWeather;
    }

    public void setLastUpdatedWeather(LocalDateTime lastUpdatedWeather) {
        this.lastUpdatedWeather = lastUpdatedWeather;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", city=" + city +
                ", data='" + data + '\'' +
                '}';
    }
}
