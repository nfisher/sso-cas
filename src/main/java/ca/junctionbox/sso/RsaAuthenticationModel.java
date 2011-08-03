package ca.junctionbox.sso;

public class RsaAuthenticationModel {
    String _code;
    String _username;

    public RsaAuthenticationModel(String $username, String $code) {
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
