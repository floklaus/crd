package com.crd.carrental.exception;

import lombok.Data;

@Data

public class ParameterInvalidException extends RuntimeException {

    public ParameterInvalidException(String message) {
        super(message);
    }
}
