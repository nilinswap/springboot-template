package com.template.sbtemplate.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DBCredentials {

    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "user")
    private String user;
    @JsonProperty(value = "password")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
