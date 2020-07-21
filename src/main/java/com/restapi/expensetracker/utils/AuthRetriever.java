package com.restapi.expensetracker.utils;

import com.restapi.expensetracker.Constants;
import com.restapi.expensetracker.exceptions.ForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthRetriever {

    private final HttpServletRequest httpRequest;
    private String token;
    private String authHeader;

    private AuthRetriever(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static AuthRetriever init(HttpServletRequest httpRequest) {
        return new AuthRetriever(httpRequest);
    }

    public AuthRetriever setAuthHeader() throws ForbiddenException {
        authHeader = Optional.ofNullable(httpRequest.getHeader("Authorization"))
            .orElseThrow(() -> new ForbiddenException("Authorization token must be provided"));
        return this;
    }

    public AuthRetriever splitHeaderStringForToken() throws ForbiddenException {
        final String[] tokenArray = authHeader.split("Bearer");
        if (tokenArray.length < 2 || tokenArray[1] == null)
            throw new ForbiddenException("Authorization token must be Bearer [token]");
        token = tokenArray[1];
        return this;
    }

    public void setUserIdToRequest() throws ForbiddenException {
        try {
            Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                    .parseClaimsJws(token).getBody();
            httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
        } catch (ForbiddenException e) {
            throw new ForbiddenException("Invalid/expired token");
        }
    }
}
