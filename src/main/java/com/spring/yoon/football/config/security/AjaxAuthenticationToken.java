package com.spring.yoon.football.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AjaxAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private Object credentials;

    /*인증 전 AuthenticationToken*/
    public AjaxAuthenticationToken(Object principal,Object credentials ){
            super((Collection)null);
            this.principal=principal;
            this.credentials=credentials;
            super.setAuthenticated(false);
    }
    /*인증 후 AuthenticationToken*/
    public AjaxAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities ){
           super(authorities);
           this.principal=principal;
           this.credentials=credentials;
           super.setAuthenticated(true);
       }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
