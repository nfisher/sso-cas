package ca.junctionbox.sso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.junctionbox.sso.models.Configurable;
import ca.junctionbox.sso.models.AppConfig;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CasSite implements Configurable {
    private Connector _connector;
    private AppConfig _config;

    public Connector connector() {
        if(_connector == null) {
            _connector = new Connector();
        }

        return _connector;
    }

    public void connector(Connector $connector) {
        _connector = $connector;
    }


    // TODO: Refactor to separate page requests and document processing.
	public boolean login(String $username, String $password) throws IOException {
		Document login_page = connector().get(_config.site());
		Element form = login_page.getElementsByTag("form").first();

		HashMap<String,String> data = new HashMap<String,String>();

		Elements inputs = form.getElementsByTag("input");
		for(Element input : inputs) {
			if(!input.attr("name").equals("reset")) {
				data.put(input.attr("name"), input.attr("value"));
			}
		}

		data.put("username", $username);
		data.put("password", $password);

		connector().post(connector().redirectURL(), data, connector().cookies());
        return connector().isOkay();
	}

    @Override
    public void config(AppConfig $config) {
        _config = $config;
    }

    public AppConfig config() {
        return _config;
    }

	public Document navigateTo(String $path) throws IOException {
		return connector().get(_config.site() + $path, connector().cookies());
	}

    public Document postTo(String $action, Map<String,String> $data) throws IOException {
        return connector().post(_config.site() + $action, $data, connector().cookies());
    }
}