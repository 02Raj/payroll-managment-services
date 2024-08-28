package com.tangle.payrollapp.controller;

import com.tangle.payrollapp.dto.employee.EmployeeListResponse;
import com.tangle.payrollapp.dto.employee.EmployeeRequest;
import com.tangle.payrollapp.dto.employee.EmployeeResponse;
import com.tangle.payrollapp.dto.employee.GetEmployeeResponse;
import com.tangle.payrollapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.createEmployee(employeeRequest);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.updateEmployee(employeeId, employeeRequest);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-emp/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmp(@PathVariable String employeeId, @RequestBody EmployeeRequest employeeRequest) {
        EmployeeResponse response = employeeService.updateEmp(employeeId, employeeRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get/{employeeId}")
    public ResponseEntity<GetEmployeeResponse> getEmployeeById(@PathVariable String employeeId) {
        GetEmployeeResponse response = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllByCompanyId/{companyId}")
    public ResponseEntity<EmployeeListResponse> getAllEmployeesByCompanyId(
            @PathVariable String companyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        int pageNumber = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 10;

        EmployeeListResponse response = employeeService.getAllEmployeesByCompanyId(companyId, pageNumber, pageSize);
        return ResponseEntity.ok(response);
    }





//    @GetMapping("/getAllBasicByCompanyId/{companyId}")
//    public ResponseEntity<List<EmployeeResponse>> getAllBasicEmployeesByCompanyId(@PathVariable String companyId) {
//        List<EmployeeResponse> response = employeeService.getAllBasicEmployeesByCompanyId(companyId);
//        return ResponseEntity.ok(response);
//    }
}
