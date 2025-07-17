package net.learnspringboot.journalApp.service;

import net.learnspringboot.journalApp.Contants.Placeholders;
import net.learnspringboot.journalApp.api.response.WeatherResponse;
import net.learnspringboot.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;
   // private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";  now config. in database mongodb

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
       // String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        else{
            String finalAPI = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                    finalAPI,
                    HttpMethod.GET,
                    null,
                    WeatherResponse.class
            );
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body, 300L);

            }
            return body;
        }

    }
}
