package com.restapi.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {

    private final Integer categoryId;
    private final Integer transactionId;
    private final Integer userId;
    private final Double amount;
    private final String note;
    private final Long transactionDate;
}
