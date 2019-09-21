package com.assignment.spring.entity;

import lombok.Builder;
import lombok.Getter;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 17:27
 */
@Builder
@Getter
public class ErrorOutput {
    private final int code;
    private final String serviceName;
    private final String message;
}
