package com.restapi.expensetracker.filters;

import com.restapi.expensetracker.utils.AuthRetriever;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            AuthRetriever.init(httpServletRequest)
                    .setAuthHeader()
                    .splitHeaderStringForToken()
                    .setUserIdToRequest();
        } catch (Exception e) {
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse); 
    }
}
