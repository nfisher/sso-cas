package ca.junctionbox.sso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import ca.junctionbox.sso.models.Configurable;
import ca.junctionbox.sso.models.TimeAppConfig;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CasSite implements Configurable {
    private String _site;
	private Connection _conn;

	public CasSite() {
	}

    // TODO: Refactor to separate page requests and document processing.
	public void login(String $username, String $password) throws IOException {
		Document login_page = get(_site);
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

		Document landing_page = post(_conn.response().url().toString(), data, _conn.response().cookies());
	}

    @Override
    public void applyConfig(TimeAppConfig $config) {
        _site = $config.site();
    }

	public Document get(String $url) throws IOException {
		_conn = Jsoup.connect($url);
		return _conn.get();
	}

	public Document get(String $url, Map $cookies) throws IOException {
		_conn = Jsoup.connect($url);

		for(Iterator it = $cookies.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			_conn.cookie((String)entry.getKey(), (String)entry.getValue());
		}

		return _conn.get();
	}

	public Document post(String $url, Map<String,String> $data, Map<String,String> $cookies) throws IOException {
		_conn = Jsoup.connect($url);

		for(Iterator it = $cookies.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			_conn.cookie((String)entry.getKey(), (String)entry.getValue());
		}

		_conn.data($data);
		return _conn.post();
	}

	public Document navigateTo(String path) throws IOException {
		return get(_site + path, _conn.response().cookies());
	}
}