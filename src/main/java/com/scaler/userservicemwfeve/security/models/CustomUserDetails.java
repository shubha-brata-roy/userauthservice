package com.scaler.userservicemwfeve.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userservicemwfeve.models.Role;
import com.scaler.userservicemwfeve.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @Setter
    @Getter
    private Long userId;


    public CustomUserDetails() {}

    public CustomUserDetails(User user) {

        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(Role role : user.getRoles()) {
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        this.authorities = grantedAuthorities;
        this.password = user.getHashPassword();
        this.username = user.getEmail();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.userId = user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        for(Role role : user.getRoles()) {
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//
//        return grantedAuthorities;

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
