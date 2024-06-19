package org.bitcamp.devlog.dto;

import java.util.Map;

public class NaverResponse extends Account{
    private final Map<String, Object> attribute;



    public NaverResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String,Object>)attribute.get("response");
    }




    @Override
    public Long getProviderType() {
        return (Long) attribute.get("naver");
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

    public String getFile() {
        return (String) attribute.get("file");
    }
}
