package com.anasxxd.videogameshop.users;

public class User {
    private Long userID;
    private final UserRole userRole;
    private String loginKey;
    private String name;
    private String password;
    private String email;

    public User(Long userID, UserRole userRole, String loginKey,
                String name, String password, String email) {
        this.userID = userID;
        this.userRole = userRole;
        this.loginKey = loginKey;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public UserRole getRole() {
        return userRole;
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
