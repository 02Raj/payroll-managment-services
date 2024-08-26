package com.tangle.payrollapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String exceptionMessage = (String) request.getAttribute("exception");
        String message = "Full authentication is required to access this resource.";

        if (exceptionMessage != null) {
            message = exceptionMessage;
        } else if (authException.getMessage().contains("Bad credentials")) {
            message = "Invalid username or password.";
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{ \"error\": \"Unauthorized\", \"message\": \"" + message + "\" }");
    }
}