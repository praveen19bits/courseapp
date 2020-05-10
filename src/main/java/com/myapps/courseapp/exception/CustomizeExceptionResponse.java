package com.myapps.courseapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/* This class will handle the CourseNotFoundException and any generic exception thrown by an application.
Any customize exception should be handles here.
@ControllerAdvice indicates that this class can be used by any Controller in this application.
*/

@ControllerAdvice
public class CustomizeExceptionResponse extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String defaultMessage = null;
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            defaultMessage = error.getDefaultMessage();
        }
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), defaultMessage, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public final ResponseEntity<Object> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest req) {
        ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InstructorNotFoundException.class)
    public final ResponseEntity<Object> handleInstructorNotFoundException(InstructorNotFoundException ex, WebRequest req) {
        ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

}
