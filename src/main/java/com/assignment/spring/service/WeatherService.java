package com.assignment.spring.service;

import com.assignment.spring.entity.InputEntity;
import com.assignment.spring.entity.ResponseEntity;

/**
 * User: Ronaldo Tiou
 * Date: 20/09/2019
 * Time: 16:17
 */
public interface WeatherService {
    ResponseEntity fetchWeather(InputEntity inputEntity);
}
