package com.lib.restfulspring.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{
    public ApplicationException(String message) {
        super(message);
    }
}
