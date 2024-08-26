package com.tangle.payrollapp.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

//@Configuration
//public class MongoConfig {
//
//    @Bean
//    public MongoClient mongoClient() {
//
//        return MongoClients.create("mongodb+srv://divyanshr243:ZcfM9WPuA0ia6MS2@cluster0.abrvr.mongodb.net/payroll?retryWrites=true&w=majority&appName=Cluster0");
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongoClient(), "payroll"); // Assuming 'payroll' is your database name
//    }
//}