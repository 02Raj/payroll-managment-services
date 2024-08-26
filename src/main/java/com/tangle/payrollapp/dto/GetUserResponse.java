package com.tangle.payrollapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    private String message;
    private Result result;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private String userId;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String roleName;
        private String roleId;
        private String companyId;
        private String companyName;
    }
}