package com.tangle.payrollapp.model.entity.employee;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmploymentInformation {

    private EmploymentType employmentType;
    private PayFrequency payFrequency;
    private Double yearlySalary;
    private Double regularRate;
    private Double otRate;
    private Double sickRate;
    private Double vacationRate;
    private LocalDate hireDate;
    private LocalDate birthDate;
    private String occupation;
}
