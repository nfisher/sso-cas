package ca.junctionbox.sso;

import java.net.URL;
import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import static java.lang.System.err;
import static java.lang.System.out;

public class CasSite {
	private String _site;
	private Connection _conn;

	public CasSite(String site) {
		_site = site;
	}

	public void login(String $username, String $password) throws IOException {
		Document login_page = get(_site);
		Element form = login_page.getElementsByTag("form").first();

		HashMap data = new HashMap();

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

	public Document get(String url) throws IOException {
		_conn = Jsoup.connect(url);
		return _conn.get();
	}

	public Document get(String url, Map cookies) throws IOException {
		_conn = Jsoup.connect(url);

		for(Iterator it = cookies.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			_conn.cookie((String)entry.getKey(), (String)entry.getValue());
		}

		return _conn.get();
	}

	public Document post(String url, Map data, Map cookies) throws IOException {
		_conn = Jsoup.connect(url);

		for(Iterator it = cookies.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			_conn.cookie((String)entry.getKey(), (String)entry.getValue());
		}

		_conn.data(data);
		return _conn.post();
	}

	public Document navigateTo(String path) throws IOException {
		return get(_site + path, _conn.response().cookies());
	}
}
