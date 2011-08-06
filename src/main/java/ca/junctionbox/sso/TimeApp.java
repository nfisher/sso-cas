package ca.junctionbox.sso;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Queue;

import ca.junctionbox.sso.commands.CommandManager;
import ca.junctionbox.sso.commands.LoadConfig;
import ca.junctionbox.sso.models.Configurable;
import ca.junctionbox.sso.models.RsaAuthentication;
import ca.junctionbox.sso.models.TimeAppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;

import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.out;


public class TimeApp {
    public final static String STARTUP = "startup";
    public final static String LOAD_CONFIG = "load_config";
    public final static String LOGIN = "login";
    public final static String LOGIN_FAILED = "login_failed";
    public final static String LOGIN_SUCCESSFUL = "login_successful";

	public static void main(String [] $args) {
        /*
        TimeAppConfig config = new TimeAppConfig();

        when(LOAD_CONFIG).run(LoadConfig.class).with(config).onSuccess(LOGIN).onFailure();
        when(LOGIN).run(Login.class).with(ui, config).onSuccess().onFailure();
        when(LOGIN_FAILED).run(LoginFailure.class).with().onSuccess().onFailure();
        when(LOGIN_SUCCESSFUL).run(LoginSucccessful.class).with();

        start(LOAD_CONFIG);
         */
		try {
            String filename = "timeapp.ini";

            if($args.length > 1)
            {
                err.println("Usage: java -jar JAR [FILE]");
                exit(1);
            } else if($args.length == 1) {
                filename = $args[0];
            }

            File ini = new File(filename);

            if(!ini.exists()) {
                err.println("Unable to locate property file: " + filename);
                err.println("Property file must contain the following properties; username, site.");
                exit(2);
            }

			CasSite site = new CasSite();
            ConsoleUI ui = new ConsoleUI(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));

            RsaAuthentication auth = ui.login();

            site.login(auth.username(), auth.code());


            /*
            RsaAuthentication auth = sulu.authenticate(config.username());

            site.login(auth.username(), auth.code());



			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String password = stdin.readLine();

			out.println("Attempting connection to CAS site as " + prop.getProperty("username", "") + ".");
            site.login(prop.getProperty("username", ""), password);

			out.println("Logged in");
			Document new_timesheet = site.navigateTo("time_sheets/new");
            out.println(new_timesheet);

            */
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
