package com.datacvg.service.serviceImpl;

import com.datacvg.service.ReaderConfig;

import java.util.Locale;
import java.util.ResourceBundle;

public class ReaderConfigImpl implements ReaderConfig {

    @Override
    public String reader(String key) {
        String value = "";
        ResourceBundle resourceBundle = ResourceBundle.getBundle("auth", Locale.CHINA);
        value = resourceBundle.getString(key);
        return value;
    }
}
