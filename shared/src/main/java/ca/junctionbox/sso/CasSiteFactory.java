package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;

public class CasSiteFactory {
    AppConfig _config;
    ConsoleUI _ui;

    public static CasSiteFactory newSite() {
        return new CasSiteFactory();
    }

    public CasSiteFactory with(AppConfig $config) {
        _config = $config;
        return this;
    }

    public CasSite build() {
        CasSite site = new CasSite();
        _config.applySiteConfig(site);
        return site;
    }
}
