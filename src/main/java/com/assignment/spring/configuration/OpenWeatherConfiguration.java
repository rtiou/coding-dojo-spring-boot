package com.assignment.spring.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 15:02
 */
@Component
@ConfigurationProperties(prefix = "configuration.open-weather")
@Data
public class OpenWeatherConfiguration {
    private String url;
}
