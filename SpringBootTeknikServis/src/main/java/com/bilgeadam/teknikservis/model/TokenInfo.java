package com.bilgeadam.teknikservis.model;

import java.util.List;
import java.util.stream.Collectors;

public class TokenInfo {
    private String username;
    private String token;
    private List<String> authorities;

    public TokenInfo() {
    }

    public TokenInfo(String username, List<String> authorities, String token) {
        this.username = username;
        this.token = token;
        this.authorities = authorities;
    }

    public String getAuthorities() {
        // çünkü react is unpredictable
        return authorities.stream().collect(Collectors.joining(","));
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}