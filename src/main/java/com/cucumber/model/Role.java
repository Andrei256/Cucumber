package com.cucumber.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER, ADMIN, SHOP;

    @Override
    public String getAuthority() {
        return name();
    }
}
