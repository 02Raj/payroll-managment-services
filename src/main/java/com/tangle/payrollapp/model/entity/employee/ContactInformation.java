package com.tangle.payrollapp.model.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactInformation {

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    @Field(name = "email")
    private String email;

    @Field(name = "mobile_phone")
    private String mobilePhone;

    @Field(name = "address")
    private String address;

    @Field(name = "city")
    private String city;

    @Field(name = "state")
    private String state;

    @Field(name = "zip_code")
    private String zipCode;

    @Field(name = "ss_number")
    private String ssNumber;
}

