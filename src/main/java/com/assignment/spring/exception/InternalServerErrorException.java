package com.assignment.spring.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 17:02
 */
@Getter
public class InternalServerErrorException extends RuntimeException {
    private HttpStatus httpStatus;
    private String body;

    public InternalServerErrorException(String message, HttpStatus httpStatus, String body) {
        super( message );
        this.body = body;
        this.httpStatus = httpStatus;
    }
}
