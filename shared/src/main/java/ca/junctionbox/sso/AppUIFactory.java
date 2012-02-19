package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AppUIFactory {
    AppConfig _config;
    private CasSite _site;

    public static AppUIFactory newConsoleUI() {
        return new AppUIFactory();
    }

    public ConsoleUI build() {
        ConsoleUI ui = new ConsoleUI(_site, new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));
        _config.applyAuthConfig(ui);
        return ui;
    }

    public AppUIFactory with(AppConfig $config) {
        _config = $config;
        return this;
    }

    public AppUIFactory with(CasSite $site) {
        _site = $site;
        return this;
    }
}
