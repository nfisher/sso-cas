package ca.junctionbox.sso.models;

import java.util.Properties;

public class TimeAppConfig extends Properties {

    public String site() {
        return getProperty("site", "");
    }

    public String username() {
        return getProperty("username", "");
    }
}