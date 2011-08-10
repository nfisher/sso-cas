package ca.junctionbox.sso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class Connector implements IConnector {
    private Connection _connection;
    private Map<String, String> _cookies;

    public Document get(String $url) throws IOException {
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
			_connection.cookie((String)entry.getKey(), (String)entry.getValue());
		}

		_connection.data($data);
		return _connection.post();
	}

    public String redirectURL() {
        return _connection.response().url().toString();
    }

    public Map<String,String> cookies() {

        if(_cookies == null) {
            _cookies = _connection.response().cookies();
        } else {
            for(String key : _connection.response().cookies().keySet()) {
                _cookies.put(key, _connection.response().cookies().get(key));
            }
        }
        return _cookies;
    }

    public boolean isOkay() {
        return _connection.response().statusCode() == 200;
    }
}
