package com.tangle.payrollapp.dto;

public class ChangePasswordResponse {

    private String message;
    private String result;

    public ChangePasswordResponse(String message, String result) {
        this.message = message;
        this.result = result;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
