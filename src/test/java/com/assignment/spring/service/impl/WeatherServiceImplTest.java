package com.assignment.spring.service.impl;

import com.assignment.spring.api.Main;
import com.assignment.spring.api.Sys;
import com.assignment.spring.api.WeatherResponse;
import com.assignment.spring.entity.InputEntity;
import com.assignment.spring.entity.ResponseEntity;
import com.assignment.spring.jpa.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 19:15
 */
@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {
    private static final String CITY_NAME = "Amsterdam";
    private static final String COUNTRY = "NL";
    private static final int CITY_ID = 1234;
    private static final double TEMPERATURE = 789.67;
    @Mock
    WebClient webClient;
    @Mock
    WeatherRepository weatherRepository;

    @InjectMocks
    WeatherServiceImpl weatherServiceImpl;

    @BeforeEach
    void init() {
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock( WebClient.RequestHeadersUriSpec.class );
        RequestBodySpec requestBodySpec = mock( RequestBodySpec.class );
        WebClient.ResponseSpec responseSpec = mock( WebClient.ResponseSpec.class );
        Mono<WeatherResponse> weatherResponseMono = mock(Mono.class);

        when( webClient.get() ).thenReturn( requestHeadersUriSpec );
        when( requestHeadersUriSpec.uri( any(  Function.class ) ) ).thenReturn( requestBodySpec );
        when( requestBodySpec.retrieve() ).thenReturn( responseSpec );
        when( responseSpec.bodyToMono( Mockito.eq( WeatherResponse.class ) ) ).thenReturn( weatherResponseMono);
        when( weatherResponseMono.block() ).thenReturn( createWeatherResponse() );
    }

    @Test
    void testFetchWeather() {
        ResponseEntity result = weatherServiceImpl.fetchWeather( InputEntity.builder().city( CITY_NAME ).build() );

        assertNotNull( result );
        assertEquals( CITY_ID, result.getId().hashCode() );
        assertEquals( CITY_NAME, result.getCity() );
        assertEquals( COUNTRY, result.getCountry() );
        assertEquals( TEMPERATURE, result.getTemperature().doubleValue() );
    }

    private WeatherResponse createWeatherResponse(){
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setId( CITY_ID );
        weatherResponse.setName( CITY_NAME);
        weatherResponse.setSys( new Sys() );
        weatherResponse.getSys().setCountry( COUNTRY );
        weatherResponse.setMain( new Main() );
        weatherResponse.getMain().setTemp( TEMPERATURE );

        return weatherResponse;
    }

}