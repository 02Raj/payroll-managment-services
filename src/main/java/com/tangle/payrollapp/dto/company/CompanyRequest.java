package com.tangle.payrollapp.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    private String companyName;
    private String einFed;
    private String eiNstate;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String website;
    private String accountNumber;
    private String routingNumber;
    private String fax;
    private String contact;
    private String businessType;
    private String companyType;
    private String owner;
    private Double fedUnEmpRate;
    private Double stateUnEmpRate;
    private String frequency;


    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
