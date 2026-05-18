package com.anasxxd.videogameshop.users.dto;

public class UpdateUserRequest {
    private String loginKey;
    private String name;
    private String password;
    private String email;

    public UpdateUserRequest() {}

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("""
                The user loginKey: %s
                The user name: %s
                The user email: %s
                """, getLoginKey(), getName(), getEmail());
    }
}
