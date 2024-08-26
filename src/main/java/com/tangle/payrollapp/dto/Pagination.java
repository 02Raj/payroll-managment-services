package com.tangle.payrollapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pagination {
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
}
