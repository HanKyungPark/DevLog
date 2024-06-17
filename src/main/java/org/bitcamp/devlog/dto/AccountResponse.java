package org.bitcamp.devlog.dto;

import java.util.Map;

public class AccountResponse extends Account {

    private final Map<String, Object> attribute;

    public AccountResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("response");
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
        return attribute.get("name").toString();
    }

    @Override
    public Long getAccountId() {
        return (Long) attribute.get("id");
    }
}



