package com.tangle.payrollapp.controller;

import com.tangle.payrollapp.dto.*;
import com.tangle.payrollapp.exception.UserServiceException;
import com.tangle.payrollapp.model.entity.User;
import com.tangle.payrollapp.service.CustomUserDetailsService;
import com.tangle.payrollapp.service.UserService;
import com.tangle.payrollapp.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUser createUserRequest) {
        logger.info("Reached createUser endpoint");
        logger.debug("Received CreateUserRequest: {}", createUserRequest);

        try {
            CreateUserResponse response = userService.createUser(
                    createUserRequest.getUsername(),
                    createUserRequest.getPassword(),
                    createUserRequest.getFirstName(),
                    createUserRequest.getLastName(),
                    createUserRequest.getCompanyId(),
                    createUserRequest.getRole(),
                    createUserRequest.getEmail()
            );

            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("User creation failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(new CreateUserResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during user creation", e);
            return ResponseEntity.status(500).body(new CreateUserResponse("An unexpected error occurred", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest.getUserName());

        try {
            LoginResponse loginResponse = userService.loginService(loginRequest.getUserName(), loginRequest.getPassword());
            return ResponseEntity.ok(loginResponse);
        } catch (UserServiceException e) {
            logger.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(401).body(new LoginResponse(e.getMessage(), null, null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during login", e);
            return ResponseEntity.status(500).body(new LoginResponse("An unexpected error occurred", null, null));
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable String userId, @RequestBody UpdateUserRequest updateUserRequest) {
        logger.info("Updating user with ID: {}", userId);

        try {
            UpdateUserResponse response = userService.updateUser(
                    userId,
                    updateUserRequest.getUsername(),
                    updateUserRequest.getPassword(),
                    updateUserRequest.getFirstName(),
                    updateUserRequest.getLastName(),
                    updateUserRequest.getCompanyId(),
                    updateUserRequest.getRole(),
                    updateUserRequest.getEmail()
            );

            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("User update failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(new UpdateUserResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during user update", e);
            return ResponseEntity.status(500).body(new UpdateUserResponse("An unexpected error occurred", null));
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching users", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            ChangePasswordResponse response = userService.changePassword(
                    request.getUserId(),
                    request.getCurrentPassword(),
                    request.getNewPassword()
            );
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("Password change failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(new ChangePasswordResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during password change", e);
            return ResponseEntity.status(500).body(new ChangePasswordResponse("An unexpected error occurred", null));
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ChangePasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            ChangePasswordResponse response = userService.resetPassword(
                    request.getUserId(),
                    request.getPassword()
            );
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("Password reset failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(new ChangePasswordResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during password reset", e);
            return ResponseEntity.status(500).body(new ChangePasswordResponse("An unexpected error occurred", null));
        }
    }

    @PostMapping("/searchUsers")
    public ResponseEntity<SearchResponse> searchUsers(@RequestBody SearchRequest request) {
        try {
            SearchResponse response = userService.searchUsers(request);
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("Search users failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(new SearchResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred during search", e);
            return ResponseEntity.status(500).body(new SearchResponse("An unexpected error occurred", null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") String userId) {
        try {
            GetUserResponse response = userService.getUserById(userId);
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            logger.error("Failed to retrieve user: {}", e.getMessage());
            return ResponseEntity.status(404).body(new GetUserResponse(e.getMessage(), null));
        } catch (Exception e) {
            logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(500).body(new GetUserResponse("An unexpected error occurred", null));
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            RefreshTokenResponse response = userService.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RefreshTokenResponse(null, null));
        }
    }
}
