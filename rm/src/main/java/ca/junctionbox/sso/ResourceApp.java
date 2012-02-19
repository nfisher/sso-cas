package ca.junctionbox.sso;

import ca.junctionbox.sso.models.AppConfig;
import ca.junctionbox.sso.ui.ConsoleUI;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static java.lang.System.err;
import static java.lang.System.exit;

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


            ArrayList<String> employeeNumbers = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream($args[0]))));

            String currentLine;
            bufferedReader.readLine(); // ignore the first line
            while((currentLine = bufferedReader.readLine()) != null) {
                String [] split = currentLine.split(",", 2);
                if(!split[0].equals("")) employeeNumbers.add(split[0]);
            }

            ui.login();
            ArrayList<String> employeeGender = new ArrayList<String>(employeeNumbers.size());

            long start = System.currentTimeMillis();
            for(String employeeNumber : employeeNumbers) {
                Document person = site.navigateTo("consultants/" + employeeNumber);
                Elements genderTags = person.select("dd:matchesOwn(^(Male|Female))");
                Element label = genderTags.first();
                String gender = "Unknown";

                // hackish way to discard trailing characters.
                if(label.text().startsWith("Male")) {
                    gender = "Male";
                } else if(label.text().startsWith("Female")) {
                    gender = "Female";
                }
                
                employeeGender.add(gender);
                System.out.println(employeeNumber + "," + gender);
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
}
