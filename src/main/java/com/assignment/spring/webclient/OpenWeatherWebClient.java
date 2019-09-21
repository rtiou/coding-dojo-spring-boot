package com.assignment.spring.webclient;

import com.assignment.spring.configuration.OpenWeatherConfiguration;
import com.assignment.spring.exception.InternalServerErrorException;
import com.assignment.spring.exception.NotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 15:05
 */
@Component
@RequiredArgsConstructor
public class OpenWeatherWebClient {
    private static final String OPEN_WEATHER_WEBCLIENT = "openWeatherWebClient";
    private final OpenWeatherConfiguration openWeatherConfiguration;

    @Bean
    public WebClient openWeatherClient() {
        return WebClient.builder()
                .baseUrl( openWeatherConfiguration.getUrl() )
                .filter( errorHandlingFilter(OPEN_WEATHER_WEBCLIENT) )
                .build();
    }

    private static ExchangeFilterFunction errorHandlingFilter(String serviceName) {
        return ExchangeFilterFunction.ofResponseProcessor( clientResponse -> {

            if (clientResponse.statusCode().isError()) {
                if (clientResponse.statusCode().is5xxServerError()) {
                    return clientResponse.bodyToMono( OpenWeatherErrorResponse.class )
                            .flatMap( errorBody -> {
                                throw new InternalServerErrorException( serviceName, clientResponse.statusCode(), errorBody.getMessage() );
                            } );
                }
                if (clientResponse.statusCode().is4xxClientError()) {
                    return clientResponse.bodyToMono( OpenWeatherErrorResponse.class )
                            .flatMap( errorBody -> {
                                throw new NotFoundException( serviceName, clientResponse.statusCode(), errorBody.getMessage() );
                            } );
                }
            }

            return Mono.just( clientResponse );
        });

    }
}

@Data
class OpenWeatherErrorResponse{
    private int cod;
    private String message;
}