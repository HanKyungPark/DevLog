package org.bitcamp.devlog.dto;

import java.util.Map;

public class AccountResponse extends Account {

    private final Map<String, Object> attribute;
    private final Map<String, Object> profile;

    public AccountResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
        this.profile = (Map<String, Object>) attribute.get("properties");
    }

    @Override
    public Long getProviderType() {
        return (Long) attribute.get("kakao");
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return profile.get("nickname").toString();
    }

    @Override
    public Long getAccountId() {
        return (Long) attribute.get("id");
    }

    public String getFile() {
        return profile.get("profile_image").toString();
    }

}



