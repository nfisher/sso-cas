package ca.junctionbox.sso.models;

import java.util.Properties;

public class AppConfig extends Properties {
    public static final String SITE = "site";
    public static final String USERNAME = "username";

    public void applySiteConfig(SiteConfigurable $site) {
        // TODO: Turn this into a object that can be updated by the AppConfig class.
        $site.siteConfig(getProperty(SITE, ""));
    }

    public void applyAuthConfig(AuthConfigurable $auth) {
        // TODO: Turn this into an object that can be updated by the AppConfig class.
        $auth.authConfig(getProperty(USERNAME, ""));
    }
}