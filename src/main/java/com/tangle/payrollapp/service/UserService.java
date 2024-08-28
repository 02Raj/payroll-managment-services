package com.tangle.payrollapp.service;

import com.tangle.payrollapp.dto.*;
import com.tangle.payrollapp.exception.UserServiceException;
import com.tangle.payrollapp.model.entity.User;


import com.tangle.payrollapp.model.entity.company.Company;
import com.tangle.payrollapp.repository.CompanyRepository;
import com.tangle.payrollapp.repository.RoleRepository;
import com.tangle.payrollapp.repository.UserRepository;
import com.tangle.payrollapp.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.tangle.payrollapp.model.entity.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    public CreateUserResponse createUser(String username, String password, String firstName, String lastName, String companyId, String roleName, String email) throws UserServiceException {

        String normalizedEmail = email.toLowerCase();

        User existingUserByEmail = userRepository.findByEmail(normalizedEmail);
        if (existingUserByEmail != null) {
            throw new UserServiceException("A user with this email already exists: " + existingUserByEmail.getUsername());
        }

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleRepository.save(role);
        }

        String encodedPassword = passwordEncoder.encode(password);

        String assignedCompanyId = (companyId != null && !companyId.trim().isEmpty()) ? companyId : null;

        User user = new User(firstName, lastName, username, encodedPassword, assignedCompanyId, normalizedEmail);
        user.setRole(role);

        userRepository.save(user);

        Map<String, String> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("roleId", role.getId());
        if (assignedCompanyId != null) {
            result.put("companyId", assignedCompanyId);
        }

        String message;
        if ("SuperAdmin".equalsIgnoreCase(roleName)) {
            message = "Super Admin user created successfully";
        } else {
            message = "User created successfully";
        }

        return new CreateUserResponse(message, result);
    }

    public LoginResponse loginService(String userName, String password) throws UserServiceException {


        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserServiceException("Invalid username or password"));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserServiceException("Invalid username or password");
        }


        Role role = user.getRole();
        if (role == null) {
            throw new UserServiceException("User role not found");
        }


        String companyName = null;
        String companyId = user.getCompanyId();
        if (companyId != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyId);
            if (companyOptional.isPresent()) {
                companyName = companyOptional.get().getCompanyName();
            }
        }


        String jwt = jwtUtil.generateToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String created = LocalDateTime.now().format(formatter);


        LoginResponse.Result result = new LoginResponse.Result(
                user.getId(),
                role.getId(),
                role.getName(),
                user.getCompanyId(),
                companyName,
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                created
        );


        System.out.println("Login successful for user: " + user.getUsername());


        String message = "Login successful";
        return new LoginResponse(message, jwt, refreshToken, result);
    }


    public UpdateUserResponse updateUser(String userId, String username, String password, String firstName, String lastName, String companyId, String roleName, String email) throws UserServiceException {
        Optional<User> optionalUser = userRepository.findById(String.valueOf(userId));
        if (!optionalUser.isPresent()) {
            throw new UserServiceException("User not found with id: " + userId);
        }

        User user = optionalUser.get();

        String normalizedEmail = email.toLowerCase();
        User existingUserByEmail = userRepository.findByEmail(normalizedEmail);
        if (existingUserByEmail != null && !existingUserByEmail.getId().equals(userId)) {
            throw new UserServiceException("A different user with this email already exists: " + existingUserByEmail.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(password);

        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCompanyId(companyId);
        user.setEmail(normalizedEmail);

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleRepository.save(role);
        }

        user.setRole(role);
        userRepository.save(user);

        Map<String, String> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("roleId", role.getId());

        String message = "User updated successfully";

        return new UpdateUserResponse(message, result);
    }

    public ChangePasswordResponse changePassword(String userId, String currentPassword, String newPassword) throws UserServiceException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserServiceException("Current password is incorrect");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);

        String message = "Password changed successfully";
        String result = "Password change successful for user: " + user.getUsername();

        return new ChangePasswordResponse(message, result);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ChangePasswordResponse resetPassword(String userId, String password) throws UserServiceException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        String message = "Password reset successfully";
        String result = "Password reset successful for user: " + user.getUsername();

        return new ChangePasswordResponse(message, result);
    }

    public SearchResponse searchUsers(SearchRequest request) throws UserServiceException {
        try {
            String keyword = request.getKeyword();
            String companyId = request.getCompanyId();
            int pageSize = request.getPageSize();
            int currentPage = request.getCurrentPage();
            String sortBy = request.getSortBy();
            String orderBy = request.getOrderBy();

            Sort sort = orderBy.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

            logger.debug("Searching for users with keyword: {}, companyId: {}", keyword, companyId);

            Page<User> usersPage = userRepository.findByCompanyIdAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    companyId, keyword, keyword, keyword, keyword, pageable);

            List<User> users = usersPage.getContent();

            List<SearchResponse.UserResult> userResults = users.stream().map(user -> {
                SearchResponse.UserResult result = new SearchResponse.UserResult();
                result.setId(user.getId());
                result.setUserName(user.getUsername());
                result.setFirstName(user.getFirstName());
                result.setLastName(user.getLastName());
                result.setEmail(user.getEmail());
                result.setIsActive(true);


                if (user.getRole() != null) {
                    result.setRole(user.getRole().getName());
                    result.setRoleId(user.getRole().getId());
                } else {
                    result.setRole("Unknown Role");
                    result.setRoleId(null);
                }


                if (user.getCompanyId() != null) {
                    Optional<Company> companyOptional = companyRepository.findById(user.getCompanyId());
                    String companyName = companyOptional.map(Company::getCompanyName).orElse("Unknown Company");
                    result.setCompany(companyName);
                    result.setCompanyId(user.getCompanyId());
                } else {
                    result.setCompany("No Company Assigned");
                    result.setCompanyId(null);
                }

                return result;
            }).collect(Collectors.toList());

            String message = "Users retrieved successfully";
            return new SearchResponse(message, userResults);
        } catch (Exception e) {
            logger.error("Error during search: ", e);
            throw new UserServiceException("An unexpected error occurred during search", e);
        }
    }


    public GetUserResponse getUserById(String userId) throws UserServiceException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException("User not found with id: " + userId));


        Role role = user.getRole();
        if (role == null) {
            throw new UserServiceException("Role not found for user with id: " + userId);
        }


        String companyName = null;
        if (user.getCompanyId() != null) {
            Optional<Company> companyOptional = companyRepository.findById(user.getCompanyId());
            companyName = companyOptional.map(Company::getCompanyName).orElse("Unknown Company");
        }


        GetUserResponse.Result result = new GetUserResponse.Result(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                role.getName(),
                role.getId(),
                user.getCompanyId(),
                companyName
        );

        return new GetUserResponse("User retrieved successfully", result);
    }


    public RefreshTokenResponse refreshToken(String refreshToken) throws UserServiceException {
        String username = jwtUtil.extractUsername(refreshToken);

        if (username == null || !jwtUtil.validateToken(refreshToken, username)) {
            throw new UserServiceException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateToken(username);
        String newRefreshToken = jwtUtil.generateRefreshToken(username);

        return new RefreshTokenResponse(newAccessToken, newRefreshToken);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
