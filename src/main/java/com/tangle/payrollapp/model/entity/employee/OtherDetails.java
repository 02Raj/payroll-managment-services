package com.tangle.payrollapp.model.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherDetails {

    @Field(name = "active")
    private Boolean active;

    @Field(name = "k401")
    private Boolean k401;

    @Field(name = "sdi")
    private Boolean sdi;

    @Field(name = "health_insurance")
    private Boolean healthInsurance;

    @Field(name = "eic")
    private Boolean eic;

    @Field(name = "gender")
    private String gender;

    @Field(name = "grade")
    private String grade;
}
