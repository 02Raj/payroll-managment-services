package com.tangle.payrollapp.model.entity.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PayFrequency {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    BI_WEEKLY("Bi-weekly"),
    MONTHLY("Monthly"),
    TWICE_A_MONTH("Twice a month");

    private final String value;

    PayFrequency(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PayFrequency fromValue(String value) {
        for (PayFrequency frequency : PayFrequency.values()) {
            if (frequency.value.equalsIgnoreCase(value)) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Unknown pay frequency: " + value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}