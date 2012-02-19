package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;

import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.in;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class ResourceApp {
    public static void main(String [] $args) {
		try {
            AppConfig config = AppConfigFactory.newConfig()
                                .withFilename("rmapp.ini")
                                .build();

            CasSite site = CasSiteFactory.newSite()
                                .with(config)
                                .build();

            ConsoleUI ui = AppUIFactory.newConsoleUI()
                                .with(config)
                                .with(site)
                                .build();

            ui.login();
            
            String [] employeeNumbers = { "12609", "10623", "13535", "13358" };

            long start = System.currentTimeMillis();
            for(String employeeNumber : employeeNumbers) {
                Document person = site.navigateTo("consultants/" + employeeNumber);
                Elements genderTags = person.select("dd:matchesOwn(^(Male|Female))");
                Element label = genderTags.first();
                System.out.println(employeeNumber + "," + label.text());
            }
            long diff = (System.currentTimeMillis() - start) / 1000;
            System.out.println("Total Time: " + diff);
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

    private String fileFromArgs(String[] $args, String $defaultFilename) {
        String filename = $defaultFilename;

        if($args.length > 1)
        {
            err.println("Usage: java -jar JAR [FILE]");
            exit(1);
        } else if($args.length == 1) {
            filename = $args[0];
        }

        return filename;
    }
}
