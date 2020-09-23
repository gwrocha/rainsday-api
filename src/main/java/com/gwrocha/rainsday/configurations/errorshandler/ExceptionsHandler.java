package com.gwrocha.rainsday.configurations.errorshandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gwrocha.rainsday.exceptions.CityAlreadySavedException;
import com.gwrocha.rainsday.exceptions.CityNotFoundException;
import com.gwrocha.rainsday.exceptions.OpenWeatherException;

@RestControllerAdvice
public class ExceptionsHandler {
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(CityAlreadySavedException.class)
	public ExceptionResponse onObjectNotFound(CityAlreadySavedException exception) {
		return new ExceptionResponse("Já existe uma cidade cadastrada com esse nome.");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CityNotFoundException.class)
	public ExceptionResponse onObjectNotFound(CityNotFoundException exception) {
		return new ExceptionResponse("Não foi possível encontrar uma cidade com o nome informado.");
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler(OpenWeatherException.class)
	public ExceptionResponse onObjectNotFound(OpenWeatherException exception) {
		return new ExceptionResponse("Não foi possível se comunicar com o serço de informações climáticas.");
	}
	
}
