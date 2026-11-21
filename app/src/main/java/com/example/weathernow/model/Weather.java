package com.example.weathernow.model;

public class Weather {
    private String cityName;
    private String region;
    private String country;
    private String condition;
    private String temperatureC;
    private String temperatureF;
    private String feelsLike;
    private String humidity;
    private String windSpeed;
    public Weather(
            String cityName,
            String region,
            String country,
            String condition,
            String temperatureC,
            String temperatureF,
            String feelsLike,
            String humidity,
            String windSpeed)
    {

        this.cityName = cityName;
        this.region = region;
        this.country = country;
        this.condition = condition;
        this.temperatureC = temperatureC;
        this.temperatureF = temperatureF;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getCityName() {
        return cityName;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getCondition() {
        return condition;
    }

    public String getTemperatureC() {
        return temperatureC;
    }

    public String getTemperatureF() {
        return temperatureF;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
