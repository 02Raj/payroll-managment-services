package com.tangle.payrollapp.dto;

import java.util.List;

public class SearchResponse {
    private String message;
    private List<UserResult> result;

    public SearchResponse(String message, List<UserResult> result) {
        this.message = message;
        this.result = result;
    }

    // Getters and Setters for SearchResponse class
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserResult> getResult() {
        return result;
    }

    public void setResult(List<UserResult> result) {
        this.result = result;
    }

    public static class UserResult {
        private String id;
        private String userName;
        private String firstName;
        private String lastName;
        private String email;
        private boolean isActive;
        private String role;
        private String roleId;
        private String company;
        private String companyId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }
    }
}
