package com.tangle.payrollapp.model.entity.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EmploymentType {
    REGULAR("Regular"),
    _1099("1099"),
    EXEMPT("Exempt");

    private final String value;

    EmploymentType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EmploymentType fromValue(String value) {
        for (EmploymentType type : EmploymentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown employment type: " + value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}