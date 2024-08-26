package com.tangle.payrollapp.exception;

public class UserServiceException extends Exception{

    public UserServiceException(String message){
        super(message);
    }
    public UserServiceException(Exception message){
        super(message);
    }
}
