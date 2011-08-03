package ca.junctionbox.sso;

import java.util.Properties;

public class TimeAppConfig {
    Properties _iniProperties;

    public TimeAppConfig(Properties $iniProperties) {
        _iniProperties = $iniProperties;
    }

    public String site() {
        return _iniProperties.getProperty("site", "");
    }

    public String username() {
        return _iniProperties.getProperty("username", "");
    }
}