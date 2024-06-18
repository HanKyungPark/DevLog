package org.bitcamp.devlog.dto;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class GoogleResponse extends Account {

    private final Map<String,Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public Long getProviderType() {
        return (Long) attribute.get("google");
    }
    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public Long getAccountId() {
        return (Long) attribute.get("sub");
    }

    public String getFile() {
        return attribute.get("profile_image").toString();
    }

}
