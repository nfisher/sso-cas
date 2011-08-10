package ca.junctionbox.sso;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public interface IConnector {
    Document get(String $url) throws IOException;
    Document get(String $url, Map $cookies) throws IOException;
    Document post(String $url, Map<String, String> $data, Map<String,String> $cookies) throws IOException;
    String redirectURL();
    Map<String, String> cookies();
    boolean isOkay();
}
