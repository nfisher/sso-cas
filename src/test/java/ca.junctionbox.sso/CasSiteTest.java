package ca.junctionbox.sso;

import static org.testng.Assert.*;
import org.testng.annotations.*;

public class CasSiteTest {
	private CasSite _site;

	@BeforeMethod
	public void setup() {
		_site = new CasSite("https://te.thoughtworks.com/");
	}

	@AfterMethod
	public void teardown() {
		_site = null;
	}
	
	@Test
	public void test_get() {
		String expected = "";
		String actual = "";

		assertEquals(actual, expected);
	}
}
