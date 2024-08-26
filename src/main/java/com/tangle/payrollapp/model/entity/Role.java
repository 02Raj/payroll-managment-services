package com.tangle.payrollapp.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {

    @Id
    private String id;
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
        if ("SuperAdmin".equalsIgnoreCase(name)) {
            this.id = "1";
        } else if ("CompanyAdmin".equalsIgnoreCase(name)) {
            this.id = "2";
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}