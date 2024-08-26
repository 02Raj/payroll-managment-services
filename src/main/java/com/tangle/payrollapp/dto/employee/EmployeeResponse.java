package com.tangle.payrollapp.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse {
    private String message;
    private Result result;

    @Data
    @AllArgsConstructor
    public static class Result {
        private String employeeId;
        private String companyId;
    }
}
