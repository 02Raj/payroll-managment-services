package com.tangle.payrollapp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tangle.payrollapp.dto.Pagination;
import com.tangle.payrollapp.dto.company.CompanyDetailsResponse;
import com.tangle.payrollapp.dto.company.CompanyRequest;
import com.tangle.payrollapp.dto.company.CompanyResponse;
import com.tangle.payrollapp.model.entity.Role;
import com.tangle.payrollapp.model.entity.User;
import com.tangle.payrollapp.model.entity.company.*;
import com.tangle.payrollapp.repository.CompanyRepository;
import com.tangle.payrollapp.repository.RoleRepository;
import com.tangle.payrollapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

//    public CompanyResponse createCompany(CompanyRequest request) {
//
//        Address address = new Address(
//                request.getAddress1(),
//                request.getAddress2(),
//                request.getCity(),
//                request.getState(),
//                request.getZipCode()
//        );
//
//
//        BankAccount bankAccount = new BankAccount(
//                request.getRoutingNumber(),
//                request.getAccountNumber()
//        );
//
//
//        Taxes taxes = new Taxes(
//                request.getEinFed(),
//                request.getEiNstate(),
//                request.getFedUnEmpRate(),
//                request.getStateUnEmpRate()
//        );
//
//
//        OtherDetails otherDetails = new OtherDetails(
//                true,
//                request.getFrequency(),
//                request.getCompanyType(),
//                request.getBusinessType(),
//                request.getFax(),
//                request.getContact()
//        );
//
//
//        Company company = new Company(
//                request.getName(),
//                request.getEmail(),
//                request.getOwner(),
//                address,
//                bankAccount,
//                taxes,
//                otherDetails
//        );
//
//
//        companyRepository.save(company);
//
//
//        String message = "Company created successfully";
//        String result = "Company ID: " + company.getId();
//
//        return new CompanyResponse(message, result);
//    }
public CompanyResponse createAccount(CompanyRequest request) {


    Optional<Company> existingCompanyByEmail = companyRepository.findByEmail(request.getEmail());
    if (existingCompanyByEmail.isPresent()) {
        throw new IllegalStateException("A company with this email already exists: " + request.getEmail());
    }


    Optional<User> existingUserByUsername = userRepository.findByUsername(request.getUsername());
    if (existingUserByUsername.isPresent()) {
        throw new IllegalStateException("A user with this username already exists: " + request.getUsername());
    }


    Company company = new Company(
            request.getCompanyName(),
            request.getEmail() != null ? request.getEmail().toLowerCase() : null,
            request.getOwner(),
            new Address(
                    request.getAddress1(),
                    request.getAddress2(),
                    request.getCity(),
                    request.getState(),
                    request.getZipCode()
            ),
            new BankAccount(
                    request.getRoutingNumber(),
                    request.getAccountNumber()
            ),
            new Taxes(
                    request.getEinFed(),
                    request.getEiNstate(),
                    request.getFedUnEmpRate() != null ? request.getFedUnEmpRate() : 0.0,
                    request.getStateUnEmpRate() != null ? request.getStateUnEmpRate() : 0.0
            ),
            new OtherDetails(
                    true,
                    request.getFrequency(),
                    request.getCompanyType(),
                    request.getBusinessType(),
                    request.getFax(),
                    request.getContact()
            ),
            request.getUsername(),
            request.getFirstName(),
            request.getLastName(),
            passwordEncoder.encode(request.getPassword())
    );


    companyRepository.save(company);


    Role companyAdminRole = roleRepository.findByName("companyAdmin");
    if (companyAdminRole == null) {
        companyAdminRole = new Role("companyAdmin");
        roleRepository.save(companyAdminRole);
    }


    User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail().toLowerCase());
    user.setCompanyId(company.getId());
    user.setCompanyName(company.getCompanyName());
    user.setRole(companyAdminRole);


    userRepository.save(user);


    String message = "Account created successfully";
    CompanyResponse.Result result = new CompanyResponse.Result(company.getId());

    return new CompanyResponse(message, Collections.singletonList(result));
}


    public CompanyResponse updateCompany(String companyId, CompanyRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalStateException("Company not found with ID: " + companyId));

        company.setCompanyName(request.getCompanyName());
        company.setEmail(request.getEmail() != null ? request.getEmail().toLowerCase() : null);
        company.setOwner(request.getOwner());

        company.setAddress(new Address(
                request.getAddress1(),
                request.getAddress2(),
                request.getCity(),
                request.getState(),
                request.getZipCode()
        ));

        company.setBankAccount(new BankAccount(
                request.getRoutingNumber(),
                request.getAccountNumber()
        ));

        company.setTaxes(new Taxes(
                request.getEinFed(),
                request.getEiNstate(),
                request.getFedUnEmpRate() != null ? request.getFedUnEmpRate() : 0.0,
                request.getStateUnEmpRate() != null ? request.getStateUnEmpRate() : 0.0
        ));

        company.setOtherDetails(new OtherDetails(
                company.getOtherDetails().isActive(),
                request.getFrequency(),
                request.getCompanyType(),
                request.getBusinessType(),
                request.getFax(),
                request.getContact()
        ));

        companyRepository.save(company);

        String message = "Company updated successfully";
        CompanyResponse.Result result = new CompanyResponse.Result(company.getId());


        return new CompanyResponse(message, Collections.singletonList(result));
    }

    public CompanyDetailsResponse getCompanyById(String companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalStateException("Company not found with ID: " + companyId));

        CompanyDetailsResponse.CompanyDetails companyDetails = new CompanyDetailsResponse.CompanyDetails(
                company.getId(),
                company.getCompanyName(),
                company.getEmail(),
                company.getOwner(),
                new CompanyDetailsResponse.AddressDetails(
                        company.getAddress().getLine1(),
                        company.getAddress().getLine2(),
                        company.getAddress().getCity(),
                        company.getAddress().getState(),
                        company.getAddress().getZipCode()
                ),
                new CompanyDetailsResponse.BankAccountDetails(
                        company.getBankAccount().getRoutingNumber(),
                        company.getBankAccount().getAccountNumber()
                ),
                new CompanyDetailsResponse.TaxDetails(
                        company.getTaxes().getFedEinNumber(),
                        company.getTaxes().getStateEinNumber(),
                        company.getTaxes().getFedUnempRate(),
                        company.getTaxes().getStateUnempRate()
                ),
                new CompanyDetailsResponse.OtherDetails(
                        company.getOtherDetails().isActive(),
                        company.getOtherDetails().getFrequency(),
                        company.getOtherDetails().getType(),
                        company.getOtherDetails().getBusiness(),
                        company.getOtherDetails().getFax(),
                        company.getOtherDetails().getContact()
                ),
                company.getUsername(),
                company.getFirstName(),
                company.getLastName()
        );

        return new CompanyDetailsResponse("Company retrieved successfully", companyDetails);
    }


    public CompanyResponse getAllCompanies(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);


        Page<Company> companyPage = companyRepository.findAll(pageable);


        List<CompanyResponse.Result> results = companyPage.getContent().stream()
                .map(company -> new CompanyResponse.Result(
                        company.getId(),
                        company.getCompanyName(),
                        company.getEmail(),
                        company.getOwner(),
                        company.getAddress(),
                        company.getBankAccount(),
                        company.getTaxes(),
                        company.getOtherDetails()
                ))
                .collect(Collectors.toList());


        Pagination pagination = new Pagination(
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages()
        );


        return new CompanyResponse("All companies retrieved successfully", results, pagination);
    }







}
