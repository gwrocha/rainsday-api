package com.gwrocha.rainsday.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gwrocha.rainsday.exceptions.CityNotFoundException;
import com.gwrocha.rainsday.exceptions.OpenWeatherException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class WeatherService {

	private static final String COD_OK = "200";
	private static final String COD_NOT_FOUND = "404";
	
	@Value("${openweather.api.key}")
	private String apiKey;
	
	@Value("${openweather.api.url}")
	private String apiUrl;
	
	public JSONObject getInfoWeatherCity(String cityName) {
		
		try {

			HttpResponse<JsonNode> response = Unirest.get(apiUrl)
				.queryString("q", cityName)
				.queryString("appid", apiKey)
				.asJson();
		
			JSONObject json = response.getBody().getObject();
			String cod = json.getString("cod");
			if(cod.equals(COD_OK)) {				
				return json;
			}

			if(cod.equals(COD_NOT_FOUND))
				throw new CityNotFoundException();

			throw new OpenWeatherException();
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		throw new OpenWeatherException();
		
	}
	
	public LocalDateTime extractDateLastInformation(JSONObject json) {
		try {
			
		JSONArray jsonArray = json.getJSONArray("list");
		Long timeInSeconds = 0L;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Long timeSec = jsonObject.getLong("dt");
			timeInSeconds = timeInSeconds < timeSec ? timeSec : timeInSeconds;
		}
		
		return LocalDateTime.ofEpochSecond(timeInSeconds, 0, ZoneOffset.of("Z"));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
