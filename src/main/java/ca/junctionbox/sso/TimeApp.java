package ca.junctionbox.sso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.jsoup.nodes.*;

import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.out;

class TimeAppUI {

    public RsaAuthenticationModel authenticate(String $defaultUser) {
        return new RsaAuthenticationModel("", "");
    }
}


public class TimeApp {
    public final static String DEFAULT_INI = "timeapp.ini";

	public static void main(String [] $args) {
		try {
            String filename = DEFAULT_INI;

            if($args.length > 1)
            {
                err.println("Usage: java -jar JAR [FILE]");
                exit(1);
            } else if($args.length == 1) {
                filename = $args[0];
            }

            if(!(new File(filename).exists())) {
                err.println("Unable to locate property file: " + filename);
                err.println("Property file must contain the following properties; username, site.");
                exit(2);
            }

			Properties prop = new Properties();
            TimeAppConfig config = new TimeAppConfig(prop);
			prop.load(new FileInputStream(filename));

			CasSite site = new CasSite(config.site());
            TimeAppUI sulu = new TimeAppUI();

            // sulu.engage();

            RsaAuthenticationModel auth = sulu.authenticate(config.username());

            site.login(auth.username(), auth.code());

            /*

			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String password = stdin.readLine();

			out.println("Attempting connection to CAS site as " + prop.getProperty("username", "") + ".");
            site.login(prop.getProperty("username", ""), password);
            */

			out.println("Logged in");
			Document new_timesheet = site.navigateTo("time_sheets/new");
            out.println(new_timesheet);

			// TODO: Evaluate the new timesheet form and test a submission.
		} catch(FileNotFoundException ex) {
			err.println("[ERROR] Unable to open configuration file.");
            exit(2);
		} catch(IOException ex) {
			err.println("[ERROR] A connection error occured.");
			ex.printStackTrace();
            exit(1);
		}

        exit(0);
	}
}
