package com.spring.yoon.football.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.yoon.football.dto.auth.SignInDto;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public  AjaxAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login",HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(!isAjax(request)){
            throw new AuthenticationServiceException("Authentication method not supported");
        }
        SignInDto signInDto = objectMapper.readValue(request.getReader(),SignInDto.class);
        String username= signInDto.getUsername();
        username= username.isEmpty() ? "":username.trim();

        String password = signInDto.getPassword();
        password= password.isEmpty() ? "":password.trim();
        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(username,password);

                     /*구현체인 providerManager의 authenticate-> AuthenticationProvider를 찾는다*/
        return this.getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }
    
    /*요청이 ajax인지 확인*/
    private boolean isAjax(HttpServletRequest request) {
         return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
     }
}
