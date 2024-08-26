package com.tangle.payrollapp.dto.company;

import com.tangle.payrollapp.dto.Pagination;
import com.tangle.payrollapp.model.entity.company.Address;
import com.tangle.payrollapp.model.entity.company.BankAccount;
import com.tangle.payrollapp.model.entity.company.OtherDetails;
import com.tangle.payrollapp.model.entity.company.Taxes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private String message;
    private List<Result> results;
    private Pagination pagination;


    public CompanyResponse(String message, List<Result> results) {
        this.message = message;
        this.results = results;
        this.pagination = null;
    }

    @Data
    @AllArgsConstructor
    public static class Result {
        private String companyId;
        private String companyName;
        private String email;
        private String owner;
        private Address address;
        private BankAccount bankAccount;
        private Taxes taxes;
        private OtherDetails otherDetails;

        public Result(String companyId) {
            this.companyId = companyId;
        }
    }
}