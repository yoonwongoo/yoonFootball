package com.spring.yoon.football.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = (String) authentication.getPrincipal();
       String password = (String) authentication.getCredentials();

        UserDetails principalMember =  userDetailsService.loadUserByUsername(String.valueOf(username));

        if(!bCryptPasswordEncoder.matches(password, principalMember.getPassword())){

            throw new BadCredentialsException("BadCredentialsException:비밀번호 및 계정이 일치하지않습니다");
        }
        return new AjaxAuthenticationToken(principalMember,null,principalMember.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(AjaxAuthenticationToken.class);
    }

}
