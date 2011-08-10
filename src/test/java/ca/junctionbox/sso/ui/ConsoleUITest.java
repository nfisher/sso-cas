package ca.junctionbox.sso.ui;

import ca.junctionbox.sso.models.RsaAuthentication;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class ConsoleUITest {
    @Test
    public void test_authenticate_should_assign_user() {
        StringWriter writer = new StringWriter();
        String str = "user\ncode\n";
        ConsoleUI ui = new ConsoleUI(new BufferedReader(new StringReader(str)), new BufferedWriter(writer));
        RsaAuthentication auth = ui.login("test");
        Assert.assertEquals(auth.username(), "user");
    }

    @Test
    public void test_authenticate_should_assign_code() {
        StringWriter writer = new StringWriter();
        String str = "user\ncode\n";
        ConsoleUI ui = new ConsoleUI(new BufferedReader(new StringReader(str)), new BufferedWriter(writer));
        RsaAuthentication auth = ui.login("test");
        Assert.assertEquals(auth.code(), "code");
    }
}
