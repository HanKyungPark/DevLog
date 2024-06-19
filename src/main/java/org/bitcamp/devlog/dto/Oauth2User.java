package org.bitcamp.devlog.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class Oauth2User implements OAuth2User {

    private final KaKaoResponse KaKaoResponse;

    private final String role;

    public Oauth2User(KaKaoResponse KaKaoResponse, String role) {
    this.KaKaoResponse = KaKaoResponse;
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

        return KaKaoResponse.getEmail();
    }



    public String getUserName() {
        return KaKaoResponse.getProviderType()+""+ KaKaoResponse.getAccountId();
    }
}
