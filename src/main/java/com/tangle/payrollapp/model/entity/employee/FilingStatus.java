package com.tangle.payrollapp.model.entity.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FilingStatus {
    HEAD_OF_HOUSEHOLD("Head of Household"),
    MARRIED_FILING_JOINTLY("Married Filing Jointly"),
    MARRIED_FILING_SEPARATELY("Married Filing Separately"),
    SINGLE("Single");

    private final String displayName;

    FilingStatus(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static FilingStatus fromString(String text) {
        String normalizedText = text.toUpperCase().replace(" ", "_");
        for (FilingStatus status : FilingStatus.values()) {
            if (status.name().equals(normalizedText)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant " + FilingStatus.class.getCanonicalName() + " with text " + text);
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}