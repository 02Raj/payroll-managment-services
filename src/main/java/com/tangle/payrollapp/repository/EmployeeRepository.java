package com.tangle.payrollapp.repository;

import com.tangle.payrollapp.model.entity.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Page<Employee> findByCompanyId(String companyId, Pageable pageable);
}