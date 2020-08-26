package com.dextra.hp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ApiError {
 
    private static final long serialVersionUID = 1L;
 
    private HttpStatus status;
    private String error;
    private Integer count;
    private List<String> errors;
}