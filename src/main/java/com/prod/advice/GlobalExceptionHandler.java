package com.prod.advice;


import com.prod.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFound(ResourceNotFoundException e){
        ApiError error=new ApiError(e.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> oneForALl(Exception e){
        ApiError error=new ApiError(e.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(400));
    }

}
