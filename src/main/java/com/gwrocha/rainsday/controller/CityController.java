package com.gwrocha.rainsday.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gwrocha.rainsday.controller.dtos.CityForm;
import com.gwrocha.rainsday.controller.dtos.CityListDto;
import com.gwrocha.rainsday.model.City;
import com.gwrocha.rainsday.services.CityService;

@RestController
@RequestMapping("cities")
public class CityController {

	@Autowired
	private CityService cityService;
	
	@GetMapping
	private List<CityListDto> getAll() {
		List<CityListDto> allDto = cityService.findAll().stream()
				.map(city -> new CityListDto(city.getId(), city.getName()))
				.collect(Collectors.toList());
		
		return allDto;
	}
	
	@GetMapping("{id}")
	private ResponseEntity<City> getOne(@PathVariable("id") Long id) {
		final Optional<City> city = cityService.findById(id);
		return ResponseEntity.of(city);
	}
	
	@PostMapping
	private ResponseEntity<City> getOne(@RequestBody CityForm cityForm) {
		City savedCity = cityService.save(cityForm.getName());
		return ResponseEntity.ok(savedCity);
	}
	
}
