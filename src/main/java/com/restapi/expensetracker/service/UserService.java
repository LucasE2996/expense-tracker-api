package com.restapi.expensetracker.service;

import com.restapi.expensetracker.domain.User;
import com.restapi.expensetracker.exceptions.EtAuthException;

public interface UserService {

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}
