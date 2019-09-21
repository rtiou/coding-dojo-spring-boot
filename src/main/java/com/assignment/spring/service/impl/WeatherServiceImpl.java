package com.assignment.spring.service.impl;

import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.InputEntity;
import com.assignment.spring.entity.ResponseEntity;
import com.assignment.spring.jpa.entity.Weather;
import com.assignment.spring.jpa.repository.WeatherRepository;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * User: Ronaldo Tiou
 * Date: 20/09/2019
 * Time: 16:36
 */

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    @Qualifier("openWeatherClient")
    private final WebClient webClient;

    private final WeatherRepository weatherRepository;

    @Override
    public ResponseEntity fetchWeather(InputEntity inputEntity) {

        WeatherResponse weatherResponse = callOpenWeatherService( inputEntity.getCity() );
        saveResponse( weatherResponse );

        return createResponseEntity( weatherResponse );
    }

    private WeatherResponse callOpenWeatherService(String city) {
        return webClient
                .get()
                .uri( uriBuilder -> uriBuilder.build( city ) )
                .retrieve()
                .bodyToMono( WeatherResponse.class )
                .block();
    }

    private ResponseEntity createResponseEntity(WeatherResponse weatherResponse) {
        return ResponseEntity.builder()
                .id( weatherResponse.getId() )
                .city( weatherResponse.getName() )
                .country( weatherResponse.getSys().getCountry() )
                .temperature( weatherResponse.getMain().getTemp())
                .build();
    }

    private void saveResponse(WeatherResponse weatherResponse) {
        Weather weatherEntity = new Weather();
        weatherEntity.setCity( weatherResponse.getName() );
        weatherEntity.setCountry( weatherResponse.getSys().getCountry() );
        weatherEntity.setId( weatherResponse.getId() );
        weatherEntity.setTemperature( weatherResponse.getMain().getTemp() );
        weatherRepository.save( weatherEntity );
    }
}
