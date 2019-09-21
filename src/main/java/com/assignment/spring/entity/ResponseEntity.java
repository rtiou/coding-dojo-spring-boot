package com.assignment.spring.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * User: Ronaldo Tiou
 * Date: 20/09/2019
 * Time: 16:34
 */
@Builder
@Getter
public class ResponseEntity {
    private final Integer id;

    private final String city;

    private final String country;

    private final Double temperature;
}
