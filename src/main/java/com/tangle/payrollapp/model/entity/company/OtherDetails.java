package com.tangle.payrollapp.model.entity.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherDetails {

    @Field(name = "is_active")
    private boolean active;

    private String frequency;
    private String type;
    private String business;
    private String fax;
    private String contact;
}
