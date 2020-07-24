package com.restapi.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {

    private Integer categoryId;
    private Integer userId;
    private String title;
    private String description;
    private Double totalExpense;
}
