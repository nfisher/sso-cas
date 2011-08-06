package ca.junctionbox.sso.ui;


import ca.junctionbox.sso.models.RsaAuthentication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ConsoleUI {
    BufferedWriter _out;
    BufferedReader _in;

    public ConsoleUI(BufferedReader $in, BufferedWriter $out) {
        _in = $in;
        _out = $out;
    }

    public RsaAuthentication login() {
        try {
            return new RsaAuthentication(username(), passcode());
        } catch(IOException $ioException) {
            // TODO: Rethrow for an exit?
        }

        return null;
    }

    protected String username() throws IOException {
        _out.write("Username: ");
        _out.flush();
        return _in.readLine();
    }

    protected String passcode() throws IOException {
        _out.write("Passcode: ");
        _out.flush();
        return _in.readLine();
    }
}
