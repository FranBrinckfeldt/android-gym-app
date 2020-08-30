package com.example.myapplication.dto;

public class AccessTokenDTO {

    private String accessToken;

    public AccessTokenDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
