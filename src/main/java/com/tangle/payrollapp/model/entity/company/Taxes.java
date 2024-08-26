package com.tangle.payrollapp.model.entity.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taxes {
    private String fedEinNumber;
    private String stateEinNumber;
    private double fedUnempRate;
    private double stateUnempRate;
}