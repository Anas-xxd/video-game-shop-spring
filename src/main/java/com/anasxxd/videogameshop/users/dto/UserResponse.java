package com.anasxxd.videogameshop.users.dto;

public class UserResponse {
    private Long id;
    private String userRole;
    private String loginKey;
    private String name;
    private String email;

    public UserResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

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
