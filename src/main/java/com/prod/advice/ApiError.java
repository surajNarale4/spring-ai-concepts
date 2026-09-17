package com.prod.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {

    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus status;


    public ApiError(){
        this.timeStamp=LocalDateTime.now();
    }
    public ApiError(String error, HttpStatus status){
        this();
        this.error=error;
        this.status=status;

    }

}
