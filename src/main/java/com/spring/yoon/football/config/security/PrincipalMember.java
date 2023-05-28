package com.spring.yoon.football.config.security;

import com.spring.yoon.football.domain.member.Member;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
public class PrincipalMember implements UserDetails {

    @Getter
    private final Member member;

    public PrincipalMember(Member member){
        this.member=member;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PrincipalMember){
            return this.member.getUsername().equals((((PrincipalMember) o).member.getUsername()));
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.member.getUsername().hashCode();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roleList = new ArrayList();
        roleList.add(()-> String.valueOf(member.getRole()));
        return roleList ;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
