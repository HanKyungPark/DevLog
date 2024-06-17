package org.bitcamp.devlog.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Oauth2User implements OAuth2User {

    private final AccountResponse AccountResponse;

    private final String role;

    public Oauth2User(AccountResponse AccountResponse, String role) {
    this.AccountResponse = AccountResponse;
    this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return role;
            }
        });
        return collection;
    }

    @Override
    public String getName() {

        return AccountResponse.getName();
    }

    public String getUserName() {
        return AccountResponse.getProviderType()+""+AccountResponse.getAccountId();
    }
}
