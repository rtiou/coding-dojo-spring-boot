package com.assignment.spring.controller.exception;

import com.assignment.spring.entity.ErrorOutput;
import com.assignment.spring.exception.InternalServerErrorException;
import com.assignment.spring.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * User: Ronaldo Tiou
 * Date: 21/09/2019
 * Time: 17:25
 */
@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int NOT_FOUND_ITEM = 1;
    private static final int INTERNAL_SERVICE_ERROR = 2;
    private static final int MISSING_REQUEST_PARAMETERS = 3;
    private static final int TYPE_MISMATCH_ERROR = 4;
    private static final String OPEN_WEATHER_WEBCLIENT = "openWeatherWebClient";

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal( ex, ErrorOutput.builder()
                        .code( NOT_FOUND_ITEM )
                        .serviceName( ex.getMessage() )
                        .message( ex.getBody() ).build(),
                new HttpHeaders(),ex.getHttpStatus(), request );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleNotFoundException(InternalServerErrorException ex, WebRequest request) {
        return handleExceptionInternal( ex, ErrorOutput.builder()
                        .code( INTERNAL_SERVICE_ERROR )
                        .serviceName( ex.getMessage() )
                        .message( ex.getBody() ).build(),
                new HttpHeaders(), ex.getHttpStatus(), request );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal( ex,
                ErrorOutput.builder()
                        .code( MISSING_REQUEST_PARAMETERS )
                        .serviceName( OPEN_WEATHER_WEBCLIENT )
                        .message( ex.getMessage() ).build(),
                headers, status, request );
    }
}
