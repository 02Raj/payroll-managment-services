package com.tangle.payrollapp.dto.employee;

import com.tangle.payrollapp.model.entity.employee.EmploymentType;
import com.tangle.payrollapp.model.entity.employee.FilingStatus;
import com.tangle.payrollapp.model.entity.employee.PayFrequency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GetEmployeeResponse {

    private String message;
    private Result result;

    @Data
    @AllArgsConstructor
    public static class Result {
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
        private String ssNumber;
        private EmploymentType employmentType;
        private String occupation;
        private PayFrequency payFrequency;
        private Double yearlySalary;
        private Double regularRate;
        private Double otRate;
        private Double sickRate;
        private Double vacationRate;
        private LocalDate hireDate;
        private LocalDate birthDate;
        private FilingStatus filingStatusFed;
        private FilingStatus filingStatusState;
        private Boolean w4Part2MultipleJobs;
        private Boolean w4Part2a;
        private Double claimDependent;
        private Double otherIncome;
        private Double deductions;
        private Double extraWithholdings;
        private Double socialSecurityRate;
        private Double medicareRate;
        private Boolean active;
        private Boolean k401;
        private Boolean sdi;
        private Boolean healthInsurance;
        private Boolean eic;
        private String gender;
        private String grade;
    }
}