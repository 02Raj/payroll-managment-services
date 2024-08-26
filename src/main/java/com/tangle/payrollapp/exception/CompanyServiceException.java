package com.tangle.payrollapp.exception;

public class CompanyServiceException extends Exception {

    public CompanyServiceException(String message){
        super(message);
    }
    public CompanyServiceException(Exception message){
        super(message);
    }
}
