package br.com.rchlo.store.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_"+ADMIN.name();
    }
}
