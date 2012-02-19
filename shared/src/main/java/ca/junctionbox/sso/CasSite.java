package ca.junctionbox.sso;

import ca.junctionbox.sso.models.SiteConfigurable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CasSite implements SiteConfigurable {
    private String _baseURL;
    // TODO: This _connection field feels dirty...
    private Connection _connection;
    private Map<String, String> _cookies;


    // TODO: Refactor to separate page requests and document processing.
	public boolean login(String $username, String $password) throws IOException {
		Document login_page = get(_baseURL);
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

        String action = baseURL() + form.attr("action");

		postTo(action, data);
        get(_connection.response().header("Location"), cookies());
        return isOkay();
	}

    private Document get(String $url) throws IOException {
        _connection = Jsoup.connect($url);
        return _connection.get();
    }

    public Document get(String $url, Map $cookies) throws IOException {
        _connection = Jsoup.connect($url);

        for(Iterator it = $cookies.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            _connection.cookie((String)entry.getKey(), (String)entry.getValue());
        }

        return _connection.get();
    }

    public Document post(String $url, Map<String,String> $data, Map<String,String> $cookies) throws IOException {
        _connection = Jsoup.connect($url);

        for(Iterator it = $cookies.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            _connection.cookie((String) entry.getKey(), (String) entry.getValue());
        }

        _connection.data($data);
        _connection.followRedirects(false);
        Document document = _connection.timeout(4000).post();

        return document;
    }

    private String baseURL() {
        // TODO: Cleanup this complicated mess.
        URL url = _connection.response().url();
        String port = (url.getPort() == -1 || (url.getProtocol() == "http" && url.getPort() == 80) || (url.getProtocol() == "https" && url.getPort() == 443)) ? "" : ":" + url.getPort();
        return url.getProtocol() + "://" + url.getHost() + port;
    }

    private boolean isOkay() {
        return _connection.response().statusCode() == 200;
    }

    public Document navigateTo(String $path) throws IOException {
		return get(_baseURL + $path, cookies());
	}

    public Map<String,String> cookies() {
        // TODO: Should move the update into the get and post methods.
        Map<String, String> cookies = _connection.response().cookies();

        if(_cookies == null) {
            _cookies = cookies;
        } else {
            for(String key : cookies.keySet()) {
                _cookies.put(key, cookies.get(key));
            }
        }

        return _cookies;
    }

    public Document postTo(String $action, Map<String, String> $data) throws IOException {
        return post($action, $data, cookies());
    }

    @Override
    public void siteConfig(String $baseURL) {
        _baseURL = $baseURL;
    }
}