package com.cucumber.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER("Пользователь"), ADMIN("Администратор"), SHOP("Магазин");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public String getRoleName() {
        return roleName;
    }
}
