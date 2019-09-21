package com.assignment.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 17:00
 */
@Getter
public class NotFoundException extends RuntimeException{
    private HttpStatus httpStatus;
    private String body;

    public NotFoundException(String message, HttpStatus httpStatus, String body) {
        super( message );
        this.body = body;
        this.httpStatus = httpStatus;
    }
}
