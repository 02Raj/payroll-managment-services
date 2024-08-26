package com.tangle.payrollapp.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsResponse {
    private String message;
    private CompanyDetails companyDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyDetails {
        private String id;
        private String companyName;
        private String email;
        private String owner;
        private AddressDetails address;
        private BankAccountDetails bankAccount;
        private TaxDetails taxes;
        private OtherDetails otherDetails;
        private String username;
        private String firstName;
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressDetails {
        private String address1;
        private String address2;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankAccountDetails {
        private String routingNumber;
        private String accountNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaxDetails {
        private String einFed;
        private String eiNstate;
        private Double fedUnEmpRate;
        private Double stateUnEmpRate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OtherDetails {
        private boolean someFlag;
        private String frequency;
        private String companyType;
        private String businessType;
        private String fax;
        private String contact;
    }
}