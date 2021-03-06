package com.restapi.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {

    private final Integer categoryId;
    private final Integer userId;
    private final String title;
    private final String description;
    private final Double totalExpense;
}
