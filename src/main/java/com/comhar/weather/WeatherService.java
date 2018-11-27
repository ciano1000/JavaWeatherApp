package com.comhar.weather;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public class WeatherService extends RestService {

    private String appID = "52f667d10e89c3b4f29966df04bc1caf";

    public WeatherService()
    {
        super();
    }

    public WeatherReport GetWeather(String lat, String lon)
    {
        try{
            HttpResponse<WeatherReport> locResponse = unirest.get("https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&APPID={appID}")
                    .routeParam("lat",lat).routeParam("lon",lon).routeParam("appID",appID).asObject(WeatherReport.class);
            WeatherReport weatherReport = locResponse.getBody();
            return  weatherReport;
        }
        catch(UnirestException e)
        {
            throw new RuntimeException(e);
        }
    }
}
