package com.tangle.payrollapp.dto;

import java.util.Map;

public class CreateUserResponse {
    private String message;
    private Map<String, String> result;

    public CreateUserResponse(String message, Map<String, String> result) {
        this.message = message;
        this.result = result;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }
}
