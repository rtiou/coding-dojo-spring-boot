package com.assignment.spring.controller;

import com.assignment.spring.api.model.WeatherResponseDto;
import com.assignment.spring.api.rest.ApiApi;
import com.assignment.spring.entity.InputEntity;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * User: Ronaldo Tiou
 * Date: 20/09/2019
 * Time: 17:29
 */
@Controller
@RequiredArgsConstructor
public class WeatherController implements ApiApi {
    private final WeatherService weatherService;

    @Override
    public ResponseEntity<WeatherResponseDto> getWeather(@NotNull @Valid String city) {
        InputEntity inputEntity = InputEntity.builder().city( city ) .build();

        return new ResponseEntity(weatherService.fetchWeather( inputEntity ),  HttpStatus.OK  ) ;
    }
}
