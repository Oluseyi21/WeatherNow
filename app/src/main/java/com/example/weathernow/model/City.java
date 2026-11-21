package com.example.weathernow.model;

public class City {
    private String name;
    private String region;
    private String weather;
    private double latitude;
    private double longitude;
    public City(String name, String region, String weather,
                double latitude, double longitude) {
        this.name = name;
        this.region = region;
        this.weather = weather;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getWeather() {
        return weather;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
