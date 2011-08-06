package ca.junctionbox.sso.models;

public class RsaAuthentication {
    String _code;
    String _username;

    public RsaAuthentication(String $username, String $code) {
        _code = $code;
        _username = $username;
    }

    public String code() {
        return _code;
    }

    public String username() {
        return _username;
    }
}
