package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AppConfigFactory {
    protected String _configFilename;

    public static AppConfigFactory newConfig() {
        return new AppConfigFactory();
    }

    public AppConfigFactory withFilename(String $configFilename) {
        _configFilename = $configFilename;
        return this;
    }

    public AppConfig build() throws IOException {
        AppConfig config = new AppConfig();
        config.load(new FileInputStream(new File(_configFilename)));
        return config;
    }
}
