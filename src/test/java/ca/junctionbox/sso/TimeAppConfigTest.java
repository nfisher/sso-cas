package ca.junctionbox.sso;

import ca.junctionbox.sso.models.TimeAppConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TimeAppConfigTest {
    TimeAppConfig _instance;

    @BeforeMethod
    public void setUp() throws Exception {
        _instance = new TimeAppConfig();
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
