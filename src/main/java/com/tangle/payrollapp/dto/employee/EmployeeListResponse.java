package com.tangle.payrollapp.dto.employee;

import com.tangle.payrollapp.dto.Pagination;
import com.tangle.payrollapp.model.entity.company.Address;
import com.tangle.payrollapp.model.entity.employee.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeListResponse {
    private String message;
    private List<EmployeeResult> results;
    private Pagination pagination;
    @Data
    @AllArgsConstructor
    public static class EmployeeResult {
        private String employeeId;
        private String companyId;
        private String firstName;
        private String lastName;
        private String email;
        private String mobilePhone;
        private String address;
        private String city;
        private String state;
        private String zipCode;
        private String occupation;
        private EmploymentType employmentType;
        private LocalDate hireDate;
        private LocalDate birthDate;

    }
}
