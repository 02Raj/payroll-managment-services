package com.tangle.payrollapp.service;

import com.tangle.payrollapp.dto.Pagination;
import com.tangle.payrollapp.dto.employee.EmployeeListResponse;
import com.tangle.payrollapp.dto.employee.EmployeeRequest;
import com.tangle.payrollapp.dto.employee.EmployeeResponse;
import com.tangle.payrollapp.dto.employee.GetEmployeeResponse;
import com.tangle.payrollapp.model.entity.employee.*;
import com.tangle.payrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {


        Employee employee = new Employee();
        employee.setCompanyId(employeeRequest.getCompanyId());


        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setFirstName(employeeRequest.getFirstName());
        contactInformation.setLastName(employeeRequest.getLastName());
        contactInformation.setEmail(employeeRequest.getEmail());
        contactInformation.setMobilePhone(employeeRequest.getMobilePhone());
        contactInformation.setAddress(employeeRequest.getAddress());
        contactInformation.setCity(employeeRequest.getCity());
        contactInformation.setState(employeeRequest.getState());
        contactInformation.setZipCode(employeeRequest.getZipCode());


        EmploymentInformation employmentInformation = new EmploymentInformation();
        employmentInformation.setEmploymentType(employeeRequest.getEmploymentType());
        employmentInformation.setPayFrequency(employeeRequest.getPayFrequency());
        employmentInformation.setYearlySalary(employeeRequest.getYearlySalary());
        employmentInformation.setRegularRate(employeeRequest.getRegularRate());
        employmentInformation.setOtRate(employeeRequest.getOtRate());
        employmentInformation.setSickRate(employeeRequest.getSickRate());
        employmentInformation.setVacationRate(employeeRequest.getVacationRate());


        employmentInformation.setHireDate(employeeRequest.getHireDate());
        employmentInformation.setBirthDate(employeeRequest.getBirthDate());


        Taxes taxes = new Taxes();
        taxes.setFilingStatusFed(employeeRequest.getFilingStatusFed()); // Use enum
        taxes.setFilingStatusState(employeeRequest.getFilingStatusState()); // Use enum
        taxes.setW4Part2MultipleJobs(employeeRequest.getW4Part2MultipleJobs());
        taxes.setW4Part2a(employeeRequest.getW4Part2a());
        taxes.setClaimDependent(employeeRequest.getClaimDependent());
        taxes.setOtherIncome(employeeRequest.getOtherIncome());
        taxes.setDeductions(employeeRequest.getDeductions());
        taxes.setExtraWithholdings(employeeRequest.getExtraWithholdings());
        taxes.setSocialSecurityRate(employeeRequest.getSocialSecurityRate());
        taxes.setMedicareRate(employeeRequest.getMedicareRate());

        // Set Other Details
        OtherDetails otherDetails = new OtherDetails();
        otherDetails.setActive(employeeRequest.getActive());
        otherDetails.setK401(employeeRequest.getK401());
        otherDetails.setSdi(employeeRequest.getSdi());
        otherDetails.setHealthInsurance(employeeRequest.getHealthInsurance());
        otherDetails.setEic(employeeRequest.getEic());
        otherDetails.setGender(employeeRequest.getGender());
        otherDetails.setGrade(employeeRequest.getGrade());

        // Set Employee Information
        employee.setContactInformation(contactInformation);
        employee.setEmploymentInformation(employmentInformation);
        employee.setTaxes(taxes);
        employee.setOtherDetails(otherDetails);

        // Save the Employee entity in the repository
        Employee savedEmployee = employeeRepository.save(employee);

        // Create the response object with the result
        String message = "Employee created successfully";
        EmployeeResponse.Result result = new EmployeeResponse.Result(savedEmployee.getId(), savedEmployee.getCompanyId());

        return new EmployeeResponse(message, result);
    }




    public EmployeeResponse updateEmployee(String employeeId, EmployeeRequest employeeRequest) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found with ID: " + employeeId));


        ContactInformation contactInformation = employee.getContactInformation();
        contactInformation.setFirstName(employeeRequest.getFirstName());
        contactInformation.setLastName(employeeRequest.getLastName());
        contactInformation.setEmail(employeeRequest.getEmail());
        contactInformation.setMobilePhone(employeeRequest.getMobilePhone());
        contactInformation.setAddress(employeeRequest.getAddress());
        contactInformation.setCity(employeeRequest.getCity());
        contactInformation.setState(employeeRequest.getState());
        contactInformation.setZipCode(employeeRequest.getZipCode());

        // Update EmploymentInformation
        EmploymentInformation employmentInformation = employee.getEmploymentInformation();
        employmentInformation.setEmploymentType(employeeRequest.getEmploymentType());
        employmentInformation.setPayFrequency(employeeRequest.getPayFrequency());
        employmentInformation.setYearlySalary(employeeRequest.getYearlySalary());
        employmentInformation.setRegularRate(employeeRequest.getRegularRate());
        employmentInformation.setOtRate(employeeRequest.getOtRate());
        employmentInformation.setSickRate(employeeRequest.getSickRate());
        employmentInformation.setVacationRate(employeeRequest.getVacationRate());
        employmentInformation.setHireDate(employeeRequest.getHireDate());
        employmentInformation.setBirthDate(employeeRequest.getBirthDate());


        // Update Taxes
        Taxes taxes = employee.getTaxes();
        taxes.setFilingStatusFed(employeeRequest.getFilingStatusFed());
        taxes.setFilingStatusState(employeeRequest.getFilingStatusState());
        taxes.setW4Part2MultipleJobs(employeeRequest.getW4Part2MultipleJobs());
        taxes.setW4Part2a(employeeRequest.getW4Part2a());
        taxes.setClaimDependent(employeeRequest.getClaimDependent());
        taxes.setOtherIncome(employeeRequest.getOtherIncome());
        taxes.setDeductions(employeeRequest.getDeductions());
        taxes.setExtraWithholdings(employeeRequest.getExtraWithholdings());
        taxes.setSocialSecurityRate(employeeRequest.getSocialSecurityRate());
        taxes.setMedicareRate(employeeRequest.getMedicareRate());

        // Update OtherDetails
        OtherDetails otherDetails = employee.getOtherDetails();
        otherDetails.setActive(employeeRequest.getActive());
        otherDetails.setK401(employeeRequest.getK401());
        otherDetails.setSdi(employeeRequest.getSdi());
        otherDetails.setHealthInsurance(employeeRequest.getHealthInsurance());
        otherDetails.setEic(employeeRequest.getEic());
        otherDetails.setGender(employeeRequest.getGender());
        otherDetails.setGrade(employeeRequest.getGrade());

        // Save the updated employee entity to the database
        Employee savedEmployee = employeeRepository.save(employee);

        String message = "Employee updated successfully";
        EmployeeResponse.Result result = new EmployeeResponse.Result(savedEmployee.getId(), employeeRequest.getCompanyId());

        return new EmployeeResponse(message, result);
    }

    public EmployeeResponse updateEmp(String employeeId, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found with id: " + employeeId));

        populateEmployeeDetails(employee, employeeRequest);
        Employee updatedEmployee = employeeRepository.save(employee);

        String message = "Employee updated successfully";
        EmployeeResponse.Result result = new EmployeeResponse.Result(updatedEmployee.getId(), employeeRequest.getCompanyId());

        return new EmployeeResponse(message, result);
    }


    public GetEmployeeResponse getEmployeeById(String employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found with id: " + employeeId));


        GetEmployeeResponse.Result result = new GetEmployeeResponse.Result(
                employee.getId(),
                employee.getCompanyId(),
                employee.getContactInformation().getFirstName(),
                employee.getContactInformation().getLastName(),
                employee.getContactInformation().getEmail(),
                employee.getContactInformation().getMobilePhone(),
                employee.getContactInformation().getAddress(),
                employee.getContactInformation().getCity(),
                employee.getContactInformation().getState(),
                employee.getContactInformation().getZipCode(),
                employee.getContactInformation().getSsNumber(),
                employee.getEmploymentInformation().getEmploymentType(),
                employee.getEmploymentInformation().getOccupation(),
                employee.getEmploymentInformation().getPayFrequency(),
                employee.getEmploymentInformation().getYearlySalary(),
                employee.getEmploymentInformation().getRegularRate(),
                employee.getEmploymentInformation().getOtRate(),
                employee.getEmploymentInformation().getSickRate(),
                employee.getEmploymentInformation().getVacationRate(),
                employee.getEmploymentInformation().getHireDate(),
                employee.getEmploymentInformation().getBirthDate(),
                employee.getTaxes().getFilingStatusFed(),
                employee.getTaxes().getFilingStatusState(),
                employee.getTaxes().getW4Part2MultipleJobs(),
                employee.getTaxes().getW4Part2a(),
                employee.getTaxes().getClaimDependent(),
                employee.getTaxes().getOtherIncome(),
                employee.getTaxes().getDeductions(),
                employee.getTaxes().getExtraWithholdings(),
                employee.getTaxes().getSocialSecurityRate(),
                employee.getTaxes().getMedicareRate(),
                employee.getOtherDetails().getActive(),
                employee.getOtherDetails().getK401(),
                employee.getOtherDetails().getSdi(),
                employee.getOtherDetails().getHealthInsurance(),
                employee.getOtherDetails().getEic(),
                employee.getOtherDetails().getGender(),
                employee.getOtherDetails().getGrade()
        );


        return new GetEmployeeResponse("Employee get successfully", result);
    }



    public EmployeeListResponse getAllEmployeesByCompanyId(String companyId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> employeePage = employeeRepository.findByCompanyId(companyId, pageable);

        List<EmployeeListResponse.EmployeeResult> results = employeePage.getContent().stream()
                .map(employee -> new EmployeeListResponse.EmployeeResult(
                        employee.getId(),
                        employee.getCompanyId(),
                        employee.getContactInformation().getFirstName(),
                        employee.getContactInformation().getLastName(),
                        employee.getContactInformation().getEmail(),
                        employee.getContactInformation().getMobilePhone(),
                        employee.getContactInformation().getAddress(),
                        employee.getContactInformation().getCity(),
                        employee.getContactInformation().getState(),
                        employee.getContactInformation().getZipCode(),
                        employee.getEmploymentInformation().getOccupation(),
                        employee.getEmploymentInformation().getEmploymentType(),
                        employee.getEmploymentInformation().getHireDate(),
                        employee.getEmploymentInformation().getBirthDate()
                ))
                .collect(Collectors.toList());

        Pagination pagination = new Pagination(
                employeePage.getNumber(),
                employeePage.getSize(),
                employeePage.getTotalElements(),
                employeePage.getTotalPages()
        );

        return new EmployeeListResponse("Employees retrieved successfully", results, pagination);
    }


//    public List<EmployeeResponse> getAllBasicEmployeesByCompanyId(String companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//
//        return employees.stream()
//                .map(employee -> new EmployeeResponse("Basic employee details retrieved successfully", new EmployeeResponse.Result(employee.getId(), employee.getCompanyId())))
//                .collect(Collectors.toList());
//    }


    private void populateEmployeeDetails(Employee employee, EmployeeRequest employeeRequest) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setFirstName(employeeRequest.getFirstName());
        contactInformation.setLastName(employeeRequest.getLastName());
        contactInformation.setEmail(employeeRequest.getEmail());
        contactInformation.setMobilePhone(employeeRequest.getMobilePhone());
        contactInformation.setAddress(employeeRequest.getAddress());
        contactInformation.setCity(employeeRequest.getCity());
        contactInformation.setState(employeeRequest.getState());
        contactInformation.setZipCode(employeeRequest.getZipCode());

        EmploymentInformation employmentInformation = new EmploymentInformation();
        employmentInformation.setEmploymentType(employeeRequest.getEmploymentType());
        employmentInformation.setPayFrequency(employeeRequest.getPayFrequency());
        employmentInformation.setYearlySalary(employeeRequest.getYearlySalary());
        employmentInformation.setRegularRate(employeeRequest.getRegularRate());
        employmentInformation.setOtRate(employeeRequest.getOtRate());
        employmentInformation.setSickRate(employeeRequest.getSickRate());
        employmentInformation.setVacationRate(employeeRequest.getVacationRate());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        employmentInformation.setHireDate(employeeRequest.getHireDate());
        employmentInformation.setBirthDate(employeeRequest.getBirthDate());


        Taxes taxes = new Taxes();
        taxes.setFilingStatusFed(employeeRequest.getFilingStatusFed());
        taxes.setFilingStatusState(employeeRequest.getFilingStatusState());
        taxes.setW4Part2MultipleJobs(employeeRequest.getW4Part2MultipleJobs());
        taxes.setW4Part2a(employeeRequest.getW4Part2a());
        taxes.setClaimDependent(employeeRequest.getClaimDependent());
        taxes.setOtherIncome(employeeRequest.getOtherIncome());
        taxes.setDeductions(employeeRequest.getDeductions());
        taxes.setExtraWithholdings(employeeRequest.getExtraWithholdings());
        taxes.setSocialSecurityRate(employeeRequest.getSocialSecurityRate());
        taxes.setMedicareRate(employeeRequest.getMedicareRate());

        OtherDetails otherDetails = new OtherDetails();
        otherDetails.setActive(employeeRequest.getActive());
        otherDetails.setK401(employeeRequest.getK401());
        otherDetails.setSdi(employeeRequest.getSdi());
        otherDetails.setHealthInsurance(employeeRequest.getHealthInsurance());
        otherDetails.setEic(employeeRequest.getEic());
        otherDetails.setGender(employeeRequest.getGender());
        otherDetails.setGrade(employeeRequest.getGrade());

        employee.setContactInformation(contactInformation);
        employee.setEmploymentInformation(employmentInformation);
        employee.setTaxes(taxes);
        employee.setOtherDetails(otherDetails);
        employee.setCompanyId(employeeRequest.getCompanyId());
    }

}