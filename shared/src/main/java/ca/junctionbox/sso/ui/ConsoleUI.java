package ca.junctionbox.sso.ui;


import ca.junctionbox.sso.CasSite;
import ca.junctionbox.sso.models.AuthConfigurable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ConsoleUI implements AuthConfigurable {
    static final String USERNAME_PROMPT_PREFIX = "Username (";
    static final String USERNAME_PROMPT_POSTFIX = "): ";
    static final String PASSCODE_PROMPT = "Passcode: ";

    BufferedWriter _out;
    BufferedReader _in;
    String _defaultUsername;
    CasSite _site;


    public ConsoleUI(CasSite $site, BufferedReader $in, BufferedWriter $out) {
        _site = $site;
        _in = $in;
        _out = $out;
    }

    public void login() throws IOException {
        _site.login(readString(usernamePrompt(), _defaultUsername), readString(PASSCODE_PROMPT, ""));
    }

    public void authConfig(String $defaultUsername) {
        _defaultUsername = $defaultUsername;
    }
    
    protected String usernamePrompt() {
        return USERNAME_PROMPT_PREFIX + _defaultUsername + USERNAME_PROMPT_POSTFIX;
    }

    public String readString(String $prompt, String $default) throws IOException {
        _out.write($prompt);
        _out.flush();
        String line = _in.readLine();
        return (line.equals("")) ? $default : line;
    }

    public int readInt(String $prompt, int $default) throws IOException {
        _out.write($prompt);
        _out.flush();
        String line = _in.readLine();
        return (line.equals("")) ? $default : Integer.parseInt(line);
    }
}
