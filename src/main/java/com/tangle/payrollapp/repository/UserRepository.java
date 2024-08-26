package com.tangle.payrollapp.repository;

import com.tangle.payrollapp.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);
    List<User> findByRole_Id(String roleId);


    Page<User> findByCompanyIdAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String companyId, String firstNameKeyword, String lastNameKeyword, String usernameKeyword, String emailKeyword, Pageable pageable);

}
