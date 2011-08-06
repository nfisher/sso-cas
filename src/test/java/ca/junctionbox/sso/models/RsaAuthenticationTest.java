package ca.junctionbox.sso.models;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;


public class RsaAuthenticationTest {
    RsaAuthentication _instance;

    @BeforeTest
    public void setUp() {
        _instance = new RsaAuthentication("user", "code");
    }

    @Test
    public void testCodeAssignment() throws Exception {
        assertEquals(_instance.code(), "code");
    }

    @Test
    public void testUsernameAssignment() throws Exception {
        assertEquals(_instance.username(), "user");
    }
}
