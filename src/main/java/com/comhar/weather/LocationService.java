package com.comhar.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

public class LocationService extends RestService {
    private String authKey = "1da134ca53a859";

    public LocationService()
    {
        super();
    }

    public Location GetLocation(String searchName)
    {
        try{
            HttpResponse<Location[]> locResponse = unirest.get("https://eu1.locationiq.com/v1/search.php?key={apiKey}&q={searchString}&format=json")
                    .routeParam("apiKey",authKey).routeParam("searchString",searchName).asObject(Location[].class);
            Location[] locations = locResponse.getBody();
            return  locations[0];
        }
        catch(UnirestException e)
        {
            throw new RuntimeException(e);
        }

    }

}
