package org.studies.SpayingAPI.models;

public class WeatherConditions {

    private String city_name;
    private String description;
    private Integer temperature;
    private Integer feels_like;
    private Integer humidity;
    private Double wind_speed;
    private String spray_condition;

    public WeatherConditions(String city_name, String description, Integer temperature, Integer feels_like, Integer humidity, Double wind_speed, String spray_condition) {
        this.city_name = city_name;
        this.description = description;
        this.temperature = temperature;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.spray_condition = spray_condition;
    }

    public WeatherConditions() {
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Integer feels_like) {
        this.feels_like = feels_like;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getSpray_condition() {
        return spray_condition;
    }

    public void setSpray_condition(String spray_condition) {
        this.spray_condition = spray_condition;
    }
}
