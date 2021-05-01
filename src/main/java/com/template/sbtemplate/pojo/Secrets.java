package com.template.sbtemplate.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Secrets {

    @JsonProperty(value = "db")
    private List<DBCredentials> dbCredentials;

    public List<DBCredentials> getDbCredentials() {
        return dbCredentials;
    }

    public void setDbCredentials(List<DBCredentials> dbCredentials) {
        this.dbCredentials = dbCredentials;
    }
}
