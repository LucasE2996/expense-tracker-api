package com.restapi.expensetracker.api;

import com.restapi.expensetracker.Constants;
import com.restapi.expensetracker.domain.User;
import com.restapi.expensetracker.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get(Constants.EMAIL);
        String password = (String) userMap.get("password");

        User user = userService.validateUser(email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get(Constants.FIRST_NAME);
        String lastName = (String) userMap.get(Constants.LAST_NAME);
        String email = (String) userMap.get(Constants.EMAIL);
        String password = (String) userMap.get(Constants.PASSWORD);

        User user = userService.registerUser(firstName, lastName, email, password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.CREATED);
    }

    private Map<String, String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim(Constants.EMAIL, user.getEmail())
                .claim(Constants.FIRST_NAME, user.getFirstName())
                .claim(Constants.LAST_NAME, user.getLastName())
                .compact();

        return ImmutableMap.of("token", token);
    }
}
