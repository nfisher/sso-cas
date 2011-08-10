package ca.junctionbox.sso.ui;


import ca.junctionbox.sso.models.RsaAuthentication;

import javax.sound.sampled.Line;
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

    public RsaAuthentication login(String $defaultUsername) {
        try {
            return new RsaAuthentication(readString("Username (" + $defaultUsername + "): ", $defaultUsername),
                                         readString("Passcode: ", ""));
        } catch(IOException $ioException) {
            // TODO: Rethrow for an exit?
        }

        return null;
    }

    public Integer readInt(String $prompt, int $default) throws IOException {
        _out.write($prompt);
        _out.flush();
        String input = _in.readLine();
        return (input.length() == 0) ? $default : Integer.parseInt(input);
    }

    public String readString(String $prompt, String $default) throws IOException {
        _out.write($prompt);
        _out.flush();
        String line = _in.readLine();
        return (line.equals("")) ? $default : line;
    }
}
