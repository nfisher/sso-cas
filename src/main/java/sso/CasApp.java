package ca.junctionbox.sso;

import ca.junctionbox.sso.CasSite;
import java.io.*;

import static java.lang.System.err;
import static java.lang.System.out;

public class CasApp {
	public static void main(String [] args) {
		try {
			out.println("Connecting to CAS site.");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			CasSite site = new CasSite("https://te.thoughtworks.com/");
			out.println("Password:");
			site.login("nfisher", stdin.readLine());

			/*
			Connection conn = Jsoup.connect("https://te.thoughtworks.com/");
			Document login_page = conn.get();


			// currently there is only one form on the RSA page
			Element form = login_page.getElementsByTag("form").first();

			Elements inputs = form.getElementsByTag("input");
			for(Element input: inputs) {
				if(input.attr("name") != "reset") {
					out.println(input.attr("name") + " = " + input.attr("value"));
					conn.data(input.attr("name"), input.attr("value"));
				}
			}
			conn.data("username", "nfisher");

			out.println("Passkey:");
			conn.data("password", stdin.readLine());

			Document landing_page = conn.post();
			
			out.println(landing_page.outerHtml());
			*/
		} catch(IOException ex) {
			err.println("[ERROR] A connection error occured.");
			ex.printStackTrace();
		}
	}
}
