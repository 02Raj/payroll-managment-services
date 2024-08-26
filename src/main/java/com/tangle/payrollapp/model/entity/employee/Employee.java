package com.tangle.payrollapp.model.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class Employee {

    @Id
    private String id;

    @Field(name = "company_id")
    private String companyId;

    @Field(name = "contact_information")
    private ContactInformation contactInformation;

    @Field(name = "employment_information")
    private EmploymentInformation employmentInformation;

    @Field(name = "taxes")
    private Taxes taxes;

    @Field(name = "other_details")
    private OtherDetails otherDetails;
}
