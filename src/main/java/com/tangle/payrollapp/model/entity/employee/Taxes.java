package com.tangle.payrollapp.model.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taxes {

    @Field(name = "filing_status_fed")
    private FilingStatus filingStatusFed;

    @Field(name = "filing_status_state")
    private FilingStatus filingStatusState;

    @Field(name = "w4_part2_multiple_jobs")
    private Boolean w4Part2MultipleJobs;

    @Field(name = "w4_part2a")
    private Boolean w4Part2a;

    @Field(name = "claim_dependent")
    private Double claimDependent;

    @Field(name = "other_income")
    private Double otherIncome;

    @Field(name = "deductions")
    private Double deductions;

    @Field(name = "extra_withholdings")
    private Double extraWithholdings;

    @Field(name = "social_security_rate")
    private Double socialSecurityRate;

    @Field(name = "medicare_rate")
    private Double medicareRate;
}