package com.tangle.payrollapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.ErrorResponse;
import org.springframework.web.client.RestTemplate;

public class AppConfig {


    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }

//    @Bean
//    public ErrorResponse getErrorResponse() {
//        return new ErrorResponse();
//    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .interceptors((request, body, execution) -> {
                    request.getHeaders().set("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MDg2NzMyMTEsImV4cCI6MTcwODY3NTAxMSwiaXNzIjoiQXBhY2hlT0ZCaXoiLCJ1c2VyTG9naW5JZCI6ImFkbWluIn0.Vmz_uklb_IZ2JUf5VcolytWEksxmBwEj6H4Bi_P4HsHjIsB0DJH2GgaCB70QszVEO2TBtd13AwHHbxFeUc8RWg");
                    return execution.execute(request, body);
                })
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Add custom configuration if needed
        // For example, to enable/disable features like FAIL_ON_UNKNOWN_PROPERTIES
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

}
