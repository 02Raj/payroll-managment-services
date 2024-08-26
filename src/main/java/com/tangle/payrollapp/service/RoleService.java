//package com.tangle.payrollapp.service;
//
//import com.tangle.payrollapp.model.entity.Role;
//import com.tangle.payrollapp.repository.RoleRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@ServiceR
//public class RoleService {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @PostConstruct
//    public void initializeRoles() {
//
//        if (!roleRepository.existsById("1")) {
//            Role superAdminRole = new Role("1", "SuperAdmin");
//            roleRepository.save(superAdminRole);
//        }
//
//
//        if (!roleRepository.existsById("2")) {
//            Role companyAdminRole = new Role("2", "companyAdmin");
//            roleRepository.save(companyAdminRole);
//        }
//    }
//}
