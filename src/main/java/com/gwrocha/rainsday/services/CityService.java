package com.gwrocha.rainsday.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwrocha.rainsday.exceptions.CityAlreadySavedException;
import com.gwrocha.rainsday.model.City;
import com.gwrocha.rainsday.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	public City save(String cityName) {
		
		cityRepository.findByName(cityName).ifPresent(city -> {throw new CityAlreadySavedException();});
		
		JSONObject infoWeatherCity = weatherService.getInfoWeatherCity(cityName);
		LocalDateTime lastInformation = weatherService.extractDateLastInformation(infoWeatherCity);
		
		City city = new City();
		city.setName(cityName);
		city.setLastInformation(infoWeatherCity.toString());
		city.setLastInformationAt(lastInformation);

		return cityRepository.save(city);
		
	}

	public List<City> findAll() {
		return cityRepository.findAll();
	}

	public Optional<City> findById(Long id) {
		return cityRepository.findById(id);
	}
	
}
