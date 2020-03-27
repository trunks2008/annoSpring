package com.hydra.exception;

public class AutowireNotFoundException extends RuntimeException{
    public AutowireNotFoundException(String msg){
        super(msg);
    }
}
