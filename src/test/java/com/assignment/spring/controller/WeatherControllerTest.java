package com.assignment.spring.controller;

import com.assignment.spring.entity.InputEntity;
import com.assignment.spring.entity.ResponseEntity;
import com.assignment.spring.exception.InternalServerErrorException;
import com.assignment.spring.exception.NotFoundException;
import com.assignment.spring.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 19:48
 */
@WebMvcTest(WeatherController.class)
@ExtendWith(SpringExtension.class)
class WeatherControllerTest {
    @MockBean
    WeatherService weatherService;

    @MockBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @InjectMocks
    WeatherController weatherController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetWeatherMissingArgument() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/api/weather") )
                .andDo( MockMvcResultHandlers.print(  ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( content().string( containsString("Required String parameter 'city' is not present")));
    }

    @Test
    void testGetWeatherCityNotFound() throws Exception {
        NotFoundException notFoundException = new NotFoundException( "City not found", HttpStatus.NOT_FOUND, "Not Found" );
        Mockito.when( weatherService.fetchWeather( Mockito.any( InputEntity.class ) ) ).thenThrow( notFoundException );
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/api/weather").param( "city", "Amsterdam" ) )
                .andDo( MockMvcResultHandlers.print(  ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( content().string( containsString("City not found")));
    }

    @Test
    void testGetWeatherInternalServerError() throws Exception {
        InternalServerErrorException internalServerErrorException = new InternalServerErrorException( "UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info." );
        Mockito.when( weatherService.fetchWeather( Mockito.any( InputEntity.class ) ) ).thenThrow( internalServerErrorException );
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/api/weather").param( "city", "Amsterdam" ) )
                .andDo( MockMvcResultHandlers.print(  ) )
                .andExpect( status().is4xxClientError() )
                .andExpect( content().string( containsString("Invalid API key")));
    }

    @Test
    void testGetWeather() throws Exception {
        Mockito.when( weatherService.fetchWeather( Mockito.any( InputEntity.class ) ) ).thenReturn( createResponseEntity() );
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/api/weather").param( "city", "Amsterdam" ) )
                .andDo( MockMvcResultHandlers.print(  ) )
                .andExpect( status().is2xxSuccessful() )
                .andExpect( content().json( "{\"id\":9876,\"city\":\"Amsterdam\",\"country\":\"NL\",\"temperature\":123.123}" ));
    }

    private ResponseEntity createResponseEntity(){
        return ResponseEntity.builder()
                .id( 9876 )
                .country( "NL" )
                .city( "Amsterdam" )
                .temperature( 123.123 ).build();
    }
}
