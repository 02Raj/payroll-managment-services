package com.tangle.payrollapp.dto;

import java.time.LocalDateTime;

public class LoginResponse {
    private String message;
    private String jwt;
    private String refreshToken; // Add this field
    private Result result;

    // Constructor with refreshToken
    public LoginResponse(String message, String jwt, String refreshToken, Result result) {
        this.message = message;
        this.jwt = jwt;
        this.refreshToken = refreshToken; // Initialize the refreshToken field
        this.result = result;
    }

    // Constructor without refreshToken (for cases where you might not need it, like in error responses)
    public LoginResponse(String message, String jwt, Result result) {
        this.message = message;
        this.jwt = jwt;
        this.result = result;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private String userId;
        private String roleId;
        private String roleName;
        private String companyName;
        private String companyId;
        private String userName;
        private String firstName;
        private String lastName;
        private String email;
        private String created;

        // Constructor for Result class
        public Result(String userId, String roleId, String roleName, String companyId, String companyName, String userName, String firstName, String lastName, String email, String created) {
            this.userId = userId;
            this.roleId = roleId;
            this.roleName = roleName;
            this.companyId = companyId;
            this.companyName = companyName;
            this.userName = userName;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.created = created;
        }

        // Getters and Setters for all fields in Result
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }
}
