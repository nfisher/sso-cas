package ca.junctionbox.sso;

import ca.junctionbox.sso.CasSite;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Properties;
import org.jsoup.nodes.*;

import static java.lang.System.err;
import static java.lang.System.out;

public class TimeApp {
	public static void main(String [] args) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("timeapp.ini"));
			out.println("Connecting to CAS site as " + prop.getProperty("username", "") + ".");

			CasSite site = new CasSite(prop.getProperty("site", ""));

			out.println("Password:");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			site.login(prop.getProperty("username", ""), stdin.readLine());
			out.println("Logged in");

			Document new_timesheet = site.navigateTo("time_sheets/new");

			// TODO: Evaluate the new timesheet form and test a submission.
		} catch(FileNotFoundException ex) {
			err.println("[ERROR] Unable to open configuration file.");
		} catch(IOException ex) {
			err.println("[ERROR] A connection error occured.");
			ex.printStackTrace();
		}
	}
}
