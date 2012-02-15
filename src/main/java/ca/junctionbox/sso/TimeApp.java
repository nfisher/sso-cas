package ca.junctionbox.sso;

import java.io.*;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import ca.junctionbox.sso.models.RsaAuthentication;
import ca.junctionbox.sso.models.AppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.lang.System.*;


public class TimeApp {
    public final static String STARTUP = "startup";
    public final static String LOAD_CONFIG = "load_config";
    public final static String LOGIN = "login";
    public final static String LOGIN_FAILED = "login_failed";
    public final static String LOGIN_SUCCESSFUL = "login_successful";

	public static void main(String [] $args) {
        /*
        AppConfig config = new AppConfig();

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

            AppConfig config = new AppConfig();
            config.load(new FileInputStream(ini));

			CasSite site = new CasSite();
            ConsoleUI ui = new ConsoleUI(new BufferedReader(new InputStreamReader(System.in)), new BufferedWriter(new OutputStreamWriter(System.out)));
            RsaAuthentication auth = ui.login(config.username());

            site.config(config);
            site.login(auth.username(), auth.code());

            Elements rows = listTimeSheets(site.navigateTo("time_sheets"));
            String href = rows.get(ui.readInt("Time sheet to clone (1): ", 1)).child(7).getElementsByTag("a").get(1).attr("href");

            Document clone = site.navigateTo(href);
            Element cloned_form = clone.getElementsByTag("form").first();
            HashMap<String, String> cloned_data = formToData(cloned_form);
            cloned_data.put("time_sheet[week_ending_date_string]", ui.readString("Week Ending (" + lastDayOfThisBusinessWeek() + "): ", lastDayOfThisBusinessWeek()));

            Document save = site.postTo(cloned_form.attr("action"), cloned_data);

            err.println(save.select("div#flash_notice").text());
            err.println(save.select("div#errorExplanation"));
		} catch(SocketTimeoutException ex) {
            err.println("[ERROR] Socket Timeout.");
            exit(3);
        } catch(FileNotFoundException ex) {
			err.println("[ERROR] Unable to open configuration file.");
            exit(2);
		} catch(IOException ex) {
			err.println("[ERROR] A connection error occurred.");
            ex.printStackTrace();
            exit(1);
		}

        exit(0);
	}

    private static HashMap<String, String> formToData(Element $cloned_form) throws IOException {
        HashMap<String, String> cloned_data = new HashMap<String, String>();

        for(Element input : $cloned_form.getElementsByTag("input")) {
            if (input.attr("type").equals("radio")) {
                if(input.attr("checked").equals("checked")) {
                    cloned_data.put(input.attr("name"), input.attr("value"));
                }
            } else if(input.attr("type").equals("submit")) {
                if(input.attr("value").endsWith("draft")) {
                    cloned_data.put(input.attr("name"), input.attr("value"));
                }
            } else {
                cloned_data.put(input.attr("name"), input.attr("value"));
            }
        }

        for(Element select : $cloned_form.getElementsByTag("select")) {
            Element selected_option = select.select("option[selected]").first();
            if(selected_option != null) {
                cloned_data.put(select.attr("name"), select.select("option[selected]").first().val());
            }
        }

        return cloned_data;
    }

    private static String lastDayOfThisBusinessWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        DateFormat date_format = new SimpleDateFormat("dd MMM yyyy");
        return date_format.format(calendar.getTime());
    }

    private static Elements listTimeSheets(Document $time_sheet_list) throws IOException {
        // TODO: Create model of time sheet list and separate UI interactions.
        Element time = $time_sheet_list.getElementById("time");
        if(time == null) {
            err.println("Uh-oh, lost track of time...think you might need to re-authenticate.");
            System.exit(1);
        }
        Elements rows = time.getElementsByTag("tr");

        // prints the header and assumes no more than 4 months of timesheets is likely to be cloned
        for(int i = 0; i < 19; i++) {
            out.println(i + ") " + rows.get(i).child(0).text() + " / " + rows.get(i).child(2).text() + " / " + rows.get(i).child(3).text());
        }

        return rows;
    }
}
