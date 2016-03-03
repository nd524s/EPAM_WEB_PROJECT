package com.epam.agency.domain;

/**
 * Created by Никита on 28.01.2016.
 */
public class User extends Entity {
    private long userId;
    private String firstName;
    private String lastName;
    private String password;
    private String login;
    private Role role;
    private String telNumber;

    public User() {
    }

    public User(String firstName, String lastName, String password, String login, String telNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.login = login;
        this.telNumber = telNumber;
    }

    public User(long userId, String firstName, String lastName, String password, String login, Role role, String telNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.login = login;
        this.role = role;
        this.telNumber = telNumber;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
