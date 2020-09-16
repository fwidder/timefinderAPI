package com.github.fwidder.timeFinder2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private final ApplicationUser applicationUser;

    public UserPrincipal(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return applicationUser.getPassword();
    }

    @Override
    public String getUsername() {
        return applicationUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return applicationUser.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return applicationUser.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return applicationUser.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return applicationUser.getEnabled();
    }
}
