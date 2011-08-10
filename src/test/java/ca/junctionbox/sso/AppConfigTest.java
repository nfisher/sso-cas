package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class AppConfigTest {
    AppConfig _instance;

    @BeforeMethod
    public void setUp() throws Exception {
        _instance = new AppConfig();
        _instance.setProperty("site", "asite");
        _instance.setProperty("username", "auser");
    }

    @Test
    public void testSite() throws Exception {
        Assert.assertEquals(_instance.site(), "asite");
    }

    @Test
    public void testUsername() throws Exception {
        Assert.assertEquals(_instance.username(), "auser");
    }
}
