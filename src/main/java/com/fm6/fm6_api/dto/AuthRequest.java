package com.fm6.fm6_api.dto;

public class AuthRequest {
    private String username;
    private String password;
    /* getters/setters */
    public String getUsername(){ return username; }
    public void setUsername(String u){ this.username = u; }
    public String getPassword(){ return password; }
    public void setPassword(String p){ this.password = p; }
}
