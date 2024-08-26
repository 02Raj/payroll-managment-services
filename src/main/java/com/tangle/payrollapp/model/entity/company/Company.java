package com.tangle.payrollapp.model.entity.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "companies")
public class Company {
    @Id
    private String id;

    @Field(name = "company_name")
    private String companyName;

    @Field(name = "email")
    private String email;

    @Field(name = "owner")
    private String owner;

    @Field(name = "address")
    private Address address;

    @Field(name = "bank_account")
    private BankAccount bankAccount;

    @Field(name = "taxes")
    private Taxes taxes;

    @Field(name = "other_details")
    private OtherDetails otherDetails;


    @Field(name = "username")
    private String username;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "password")
    private String password;





    public Company(String companyName, String email, String owner, Address address, BankAccount bankAccount, Taxes taxes, OtherDetails otherDetails, String username, String firstName, String lastName, String password) {
        this.companyName = companyName;
        this.email = email;
        this.owner = owner;
        this.address = address;
        this.bankAccount = bankAccount;
        this.taxes = taxes;
        this.otherDetails = otherDetails;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
