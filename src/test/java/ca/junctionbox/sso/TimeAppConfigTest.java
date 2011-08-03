package ca.junctionbox.sso;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;


public class TimeAppConfigTest {
    TimeAppConfig _instance;

    @BeforeMethod
    public void setUp() throws Exception {
        Properties props = new Properties();
        props.setProperty("site", "asite");
        props.setProperty("username", "auser");
        _instance = new TimeAppConfig(props);
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
