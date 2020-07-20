package com.restapi.expensetracker.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
