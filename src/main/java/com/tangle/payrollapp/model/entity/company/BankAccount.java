package com.tangle.payrollapp.model.entity.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    private String routingNumber;
    private String accountNumber;
}