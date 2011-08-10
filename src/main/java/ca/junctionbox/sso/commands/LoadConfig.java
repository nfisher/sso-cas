package ca.junctionbox.sso.commands;

import ca.junctionbox.sso.models.AppConfig;

import java.io.IOException;
import java.io.Reader;

public class LoadConfig implements Commandable {
    AppConfig _config;
    Reader _reader;
    String _error = null;

    public LoadConfig(AppConfig $config, Reader $reader) {
        _config = $config;
        _reader = $reader;
    }

    @Override
    public boolean execute() {
        if(_config == null) return false;
        if(_reader == null) return false;

        try {
            _config.load(_reader);
            return  true;
        } catch (IOException e) {
            _error = e.getMessage();
            return false;
        }
    }
}
