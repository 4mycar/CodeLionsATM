package com.serdyukov.atmservice.security;


import com.serdyukov.atmservice.entity.Card;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetails implements UserDetails {

    private String login;
    private String password;
    private Collection< ? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserEntityToCustomUserDetails(Card card) {
        CustomUserDetails c = new CustomUserDetails();
        c.login = card.getId().toString();
        c.password = String.valueOf(card.getPin());
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(card.getId().toString()));
        return c;
    }

    @Override
    public Collection< ? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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
