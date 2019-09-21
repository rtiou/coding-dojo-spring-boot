package com.assignment.spring.jpa.repository;

import com.assignment.spring.jpa.entity.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
}
