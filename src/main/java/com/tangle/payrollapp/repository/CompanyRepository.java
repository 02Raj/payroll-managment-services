package com.tangle.payrollapp.repository;

import com.tangle.payrollapp.model.entity.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByEmail(String email);


}
