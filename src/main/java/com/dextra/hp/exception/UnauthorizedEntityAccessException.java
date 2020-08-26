package com.dextra.hp.exception;

/**
 * Thrown when user tries to access an entity which he is not supposed to or allowed to.
 * For example, accessing deleted information
 */
public class UnauthorizedEntityAccessException extends Exception{
    public UnauthorizedEntityAccessException(String message){
        super(message);
    }
}
