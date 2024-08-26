package com.tangle.payrollapp.controller;

import com.tangle.payrollapp.dto.company.CompanyDetailsResponse;
import com.tangle.payrollapp.dto.company.CompanyRequest;
import com.tangle.payrollapp.dto.company.CompanyResponse;
import com.tangle.payrollapp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/createAccount")
    public ResponseEntity<CompanyResponse> createAccount(@RequestBody CompanyRequest request) {
        CompanyResponse response = companyService.createAccount(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{companyId}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable String companyId, @RequestBody CompanyRequest request) {
        CompanyResponse response = companyService.updateCompany(companyId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<CompanyDetailsResponse> getCompanyById(@PathVariable String companyId) {
        CompanyDetailsResponse response = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get/all")
    public ResponseEntity<CompanyResponse> getAllCompanies(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        CompanyResponse response = companyService.getAllCompanies(page, size);
        return ResponseEntity.ok(response);
    }
}
