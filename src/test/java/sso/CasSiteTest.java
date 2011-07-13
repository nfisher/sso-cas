package ca.junctionbox.sso;

import static org.testng.Assert.*;
import org.testng.annotations.*;

public class CasSiteTest {
	private CasSite site;

	@BeforeMethod
	public void setup() {
		site = new CasSite("https://te.thoughtworks.com/");
	}

	@AfterMethod
	public void teardown() {
		site = null;
	}
	
	@Test
	public void test_get() {
		String expected = "";
		String actual = "";

		assertEquals(actual, expected);
	}
}
