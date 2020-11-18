package org.studies.SpayingAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.studies.SpayingAPI.models.WeatherConditions;
import org.studies.SpayingAPI.models.WeatherResponse;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spray")
public class SprayController {

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @GetMapping
    public ResponseEntity<WeatherConditions> get(@RequestParam("city") String city) {
        ObjectMapper mapper = new ObjectMapper();
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.openweathermap.org/data/2.5/weather?q="+ encodedCity + "&units=metric&lang=pt&appid={YOUR_KEY}"))
                .build();

        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            WeatherResponse weatherResponse = mapper.readValue(response.body(), WeatherResponse.class);

            String description = weatherResponse.getWeather().get(0).getMain();
            Integer temperature = weatherResponse.getMain().getTemp().intValue();
            Integer feelsLike = weatherResponse.getMain().getFeelsLike().intValue();
            Integer humidity = weatherResponse.getMain().getHumidity();
            Double wind = weatherResponse.getWind().getSpeed()*3.6;
            String sprayCondition = "";

            List bad_conditions = new ArrayList();
            bad_conditions.add("Thunderstorm");
            bad_conditions.add("Drizzle");
            bad_conditions.add("Rain");
            bad_conditions.add("Snow");

            if (!(bad_conditions.contains(description)) && (temperature >= 10 && temperature <= 30) && (feelsLike >= 10 && feelsLike <= 30)
                && (humidity > 50) && (wind >= 3 && wind <= 10)) {
                sprayCondition = "Good weather conditions for spraying";
            } else {
                if (bad_conditions.contains(description)) {
                    sprayCondition = "Bad weather conditions for spraying: " + description;
                } else  if ((temperature >= 30) && (feelsLike >= 30) && (humidity > 50) && (wind >= 3 && wind <= 10)) {
                    sprayCondition = "Bad weather conditions: " + temperature + " °C is too hot for spraying";
                } else  if ((temperature < 10) && (feelsLike < 10) && (humidity > 50) && (wind >= 3 && wind <= 10)) {
                    sprayCondition = "Bad weather conditions: " + temperature + " °C is too cold for spraying";
                } else  if ((temperature >= 10 && temperature <= 30) && (feelsLike >= 10 && feelsLike <= 30) && (humidity <= 50) && (wind >= 3 && wind <= 10)) {
                    sprayCondition = "Bad weather conditions: " + humidity + " % air humidity. It is below that recommended for spraying";
                } else  if ((temperature >= 10 && temperature <= 30) && (feelsLike >= 10 && feelsLike <= 30) && (humidity > 50) && (wind < 3)) {
                    sprayCondition = "Bad weather conditions:  The wind speed of " + wind + " km/h is very low and not recommended for spraying";
                } else  if ((temperature >= 10 && temperature <= 30) && (feelsLike >= 10 && feelsLike <= 30) && (humidity > 50) && (wind > 10)) {
                    sprayCondition = "Bad weather conditions:  The wind speed of " + wind + " km/h is above the recommended and can cause drift.";
                } else {
                    sprayCondition = "Bad weather conditions for spraying";
                }
            }

            WeatherConditions weatherConditions = new WeatherConditions();

            weatherConditions.setCity_name(city);
            weatherConditions.setDescription(description);
            weatherConditions.setTemperature(temperature);
            weatherConditions.setFeels_like(feelsLike);
            weatherConditions.setHumidity(humidity);
            weatherConditions.setWind_speed(wind);
            weatherConditions.setSpray_condition(sprayCondition);


            return ResponseEntity.ok(weatherConditions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

